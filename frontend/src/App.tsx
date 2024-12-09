import { useEffect, useState } from "react";
import "./App.css";
import { Post } from "./data/classes/Post";
import { PostController } from "./controllers/PostController";
import CreatePostForm from "./components/CreatePostForm";
import "./components/CreatePostForm.css";
import { useKeycloak } from "@react-keycloak/web";

function App() {
  const [posts, setPosts] = useState<Post[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  const { keycloak, initialized } = useKeycloak();

  const handleNewPost = (newPost: Post) => {
    setPosts((prevPosts) => [newPost, ...prevPosts]);
  };

  useEffect(() => {
    // If initialized is false, we don't load posts yet
    if (!initialized) return;

    // load posts only when initialized is true
    (async () => {
      try {
        const fetchedPosts = await PostController.fetchPosts();
        setPosts(fetchedPosts);
      } catch (err) {
        setError("Failed to load posts");
      } finally {
        setLoading(false);
      }
    })();
  }, [initialized]);

  // Now conditionally render based on state AFTER calling hooks
  if (!initialized) {
    return <div>Loading Keycloak...</div>;
  }

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div className="app-container">
      {keycloak.authenticated ? (
        <>
          <h1>Welcome, {keycloak.tokenParsed?.preferred_username}!</h1>
          <button onClick={() => keycloak.logout()}>Logout</button>
        </>
      ) : (
        <button onClick={() => keycloak.login()}>Login</button>
      )}
      <h1 className="header">Post List</h1>
      <CreatePostForm onPostCreated={handleNewPost} />
      <div className="post-list">
        {posts.length > 0 ? (
          posts.map((post) => (
            <div className="post-card" key={post.id}>
              <h2 className="post-content">{post.content}</h2>
              <p className="post-location">
                Location: {post.location.latitude}, {post.location.longitude}
              </p>
              <p className="post-username">
                Username: {post.barbarianUsername}
              </p>
              <p className="post-upvotes">Upvotes: {post.upvotes}</p>
              <p className="post-downvotes">Downvotes: {post.downvotes}</p>
              <p className="post-jodel-ids">
                Jodel IDs: {(post.jodelIdList || []).join(", ")}
              </p>
            </div>
          ))
        ) : (
          <p className="no-posts">No posts available.</p>
        )}
      </div>
    </div>
  );
}

export default App;
