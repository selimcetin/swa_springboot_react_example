import React, { useState, useEffect } from "react";
import { Post } from "../types/Post";
import { PostService, setAuthToken } from "../services/PostService";
import PostElement from "./PostElement";
import { useKeycloak } from "@react-keycloak/web";
import { usePosts } from "../context/PostContext";

const PostList: React.FC = () => {
  const { keycloak, initialized } = useKeycloak();
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const { posts, dispatch } = usePosts();

  useEffect(() => {
    if (keycloak.authenticated && keycloak.token) {
      setAuthToken(keycloak.token);
    }
  }, [keycloak.authenticated, keycloak.token]);

  useEffect(() => {
    (async () => {
      try {
        const fetchedPosts = await PostService.fetchAllPosts();

        fetchedPosts.forEach((post: Post) => {
          dispatch({ type: "ADD_POST", payload: post });
        });
      } catch (err) {
        setError("Failed to load posts");
      } finally {
        setLoading(false);
      }
    })();
  }, [dispatch]);

  if (loading) return <p>Loading posts...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      {posts.length > 0 ? (
        posts.map((post) => <PostElement key={post.id} post={post} />)
      ) : (
        <p>No posts available.</p>
      )}
    </div>
  );
};

export default PostList;
