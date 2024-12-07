import { useState } from "react";
import { PostController } from "../controllers/PostController";
import { Post } from "../data/classes/Post";
import "./CreatePostForm.css";

interface CreatePostFormProps {
  onPostCreated: (newPost: Post) => void;
}

const CreatePostForm = ({ onPostCreated }: CreatePostFormProps) => {
  const [content, setContent] = useState("");
  const [username, setUsername] = useState("");
  const [latitude, setLatitude] = useState<number | "">("");
  const [longitude, setLongitude] = useState<number | "">("");
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (latitude === "" || longitude === "") {
      setError("Latitude and Longitude are required.");
      return;
    }

    const newPost = {
      content,
      barbarian: {
        username,
      },
      location: {
        longitude,
        latitude,
      },
    };

    console.log("Submitting new post:", newPost);

    try {
      const createdPost = await PostController.createPost(newPost);
      onPostCreated(createdPost);
      setContent("");
      setUsername("");
      setLatitude("");
      setLongitude("");
      setError(null);
    } catch (err) {
      console.error("Error creating post:", err);
      setError("Failed to create post. Please try again.");
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: "20px" }}>
      <h2>Create a New Post</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <div>
        <label>Content:</label>
        <input
          type="text"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Username:</label>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Latitude:</label>
        <input
          type="number"
          value={latitude}
          onChange={(e) => setLatitude(parseFloat(e.target.value))}
          required
        />
      </div>
      <div>
        <label>Longitude:</label>
        <input
          type="number"
          value={longitude}
          onChange={(e) => setLongitude(parseFloat(e.target.value))}
          required
        />
      </div>
      <button type="submit">Create Post</button>
    </form>
  );
};

export default CreatePostForm;
