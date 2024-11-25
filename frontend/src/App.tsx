import { useEffect, useState } from "react";
import "./App.css";
import { Post } from "./data/classes/Post";
import { PostController } from "./controllers/PostController";

function App() {
  const [posts, setPosts] = useState<Post[]>([]); // State to hold the list of posts
  const [error, setError] = useState<string | null>(null); // State to handle errors
  const [loading, setLoading] = useState<boolean>(true); // State to handle loading

  useEffect(() => {
    const loadPosts = async () => {
      try {
        setLoading(true);
        const fetchedPosts = await PostController.fetchPosts();
        setPosts(fetchedPosts);
      } catch (error) {
        console.error("Error fetching posts:", error);
        setError("Failed to load posts. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    loadPosts();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <h1>Post List</h1>
      {posts.length > 0 ? (
        posts.map((post) => (
          <div
            key={post.id}
            style={{
              border: "1px solid #ccc",
              margin: "10px",
              padding: "10px",
            }}
          >
            <h2>{post.content}</h2>
            <p>
              Location: {post.location.latitude}, {post.location.longitude}
            </p>
            <p>Username: {post.barbarianUsername}</p>
            <p>Upvotes: {post.upvotes}</p>
            <p>Downvotes: {post.downvotes}</p>
            <p>Jodel IDs: {post.jodelIdList.join(", ")}</p>
          </div>
        ))
      ) : (
        <p>No posts available.</p>
      )}
    </div>
  );
}

export default App;
