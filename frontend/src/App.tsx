import { useEffect } from "react";
import "./App.css";
import { useKeycloak } from "@react-keycloak/web";
import PostList from "./components/PostList";
import CreatePostForm from "./components/CreatePostForm";
import { setAuthToken } from "./services/PostService";
import { PostProvider } from "./context/PostContext";

function App() {
  const { keycloak, initialized } = useKeycloak();

  useEffect(() => {
    if (!initialized) return;
  }, [initialized]);

  useEffect(() => {
    if (keycloak.authenticated && keycloak.token) {
      setAuthToken(keycloak.token);
    }
  }, [keycloak.authenticated, keycloak.token]);

  if (!initialized) {
    return <div>Loading Keycloak...</div>;
  }

  return (
    <PostProvider>
      <div className="app-container">
        {keycloak.authenticated ? (
          <>
            <h1>Welcome, {keycloak.tokenParsed?.preferred_username}!</h1>
            <button onClick={() => keycloak.logout()}>Logout</button>
            <CreatePostForm
              username={keycloak.tokenParsed?.preferred_username}
            />
            <PostList />
          </>
        ) : (
          <button onClick={() => keycloak.login()}>Login</button>
        )}
      </div>
    </PostProvider>
  );
}

export default App;
