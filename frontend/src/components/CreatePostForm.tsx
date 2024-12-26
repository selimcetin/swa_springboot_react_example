import React, { useState, FormEvent } from "react";
import { PostService } from "../services/PostService";
import { Post } from "../types/Post";
import { Barbarian } from "../types/Barbarian";
import { usePosts } from "../context/PostContext";

interface Location {
  latitude: number;
  longitude: number;
}

interface CreatePostFormProps {
  username: string;
}

const CreatePostForm: React.FC<CreatePostFormProps> = ({ username }) => {
  const [content, setContent] = useState<string>("");
  const [location, setLocation] = useState<Location | null>(null);
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const { dispatch } = usePosts();

  const getGeolocation = (): Promise<Location> => {
    return new Promise((resolve, reject) => {
      if (!navigator.geolocation) {
        reject(new Error("Geolocation is not supported by your browser."));
      } else {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const { latitude, longitude } = position.coords;
            resolve({ latitude, longitude });
          },
          (err) => {
            switch (err.code) {
              case err.PERMISSION_DENIED:
                reject(new Error("User denied the request for Geolocation."));
              case err.POSITION_UNAVAILABLE:
                reject(new Error("Location information is unavailable."));
              case err.TIMEOUT:
                reject(
                  new Error("The request to get user location timed out.")
                );
              default:
                reject(
                  new Error(
                    "An unknown error occurred while fetching location."
                  )
                );
            }
          },
          {
            enableHighAccuracy: true,
            timeout: 5000,
            maximumAge: 0,
          }
        );
      }
    });
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setIsSubmitting(true);
    setError(null);
    setSuccessMessage(null);

    try {
      const userLocation = await getGeolocation();
      const barbarian = new Barbarian(username);
      setLocation(userLocation);

      const newPost: Omit<Post, "id"> = {
        content,
        barbarian,
        location: userLocation,
        upvotes: 0,
        downvotes: 0,
        jodelIdList: [],
      };

      console.log(newPost);
      const createdPost = await PostService.createPost(newPost);

      setContent("");
      setLocation(null);
      setSuccessMessage(`Post created successfully with ID: ${createdPost.id}`);
      dispatch({ type: "ADD_POST", payload: createdPost });
    } catch (err: any) {
      setError(err.message || "An error occurred while creating the post.");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div style={styles.container}>
      <h2>Create a New Post</h2>
      <form onSubmit={handleSubmit} style={styles.form}>
        <div style={styles.formGroup}>
          <div>{username}</div>
        </div>

        {/* Content Field */}
        <div style={styles.formGroup}>
          <label htmlFor="content" style={styles.label}>
            Content:
          </label>
          <textarea
            id="content"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
            style={styles.textarea}
            placeholder="What's on your mind?"
          />
        </div>

        {/* Location Display */}
        {location && (
          <div style={styles.formGroup}>
            <label style={styles.label}>Your Location:</label>
            <p>
              Latitude: {location.latitude.toFixed(4)}, Longitude:{" "}
              {location.longitude.toFixed(4)}
            </p>
          </div>
        )}

        {error && <p style={styles.error}>{error}</p>}

        {successMessage && <p style={styles.success}>{successMessage}</p>}

        {/* Submit Button */}
        <button type="submit" disabled={isSubmitting} style={styles.button}>
          {isSubmitting ? "Submitting..." : "Create Post"}
        </button>
      </form>
    </div>
  );
};

// Inline styles for simplicity; consider using CSS or styled-components for larger projects
const styles: { [key: string]: React.CSSProperties } = {
  container: {
    maxWidth: "500px",
    margin: "0 auto",
    padding: "1rem",
    border: "1px solid #ccc",
    borderRadius: "8px",
    backgroundColor: "#f9f9f9",
  },
  form: {
    display: "flex",
    flexDirection: "column",
  },
  formGroup: {
    marginBottom: "1rem",
  },
  label: {
    marginBottom: "0.5rem",
    display: "block",
    fontWeight: "bold",
  },
  input: {
    padding: "0.5rem",
    fontSize: "1rem",
    width: "100%",
    boxSizing: "border-box",
  },
  textarea: {
    padding: "0.5rem",
    fontSize: "1rem",
    width: "100%",
    height: "100px",
    boxSizing: "border-box",
  },
  button: {
    padding: "0.75rem",
    fontSize: "1rem",
    backgroundColor: "#4CAF50",
    color: "#fff",
    border: "none",
    borderRadius: "4px",
    cursor: "pointer",
  },
  error: {
    color: "red",
    marginBottom: "1rem",
  },
  success: {
    color: "green",
    marginBottom: "1rem",
  },
};

export default CreatePostForm;
