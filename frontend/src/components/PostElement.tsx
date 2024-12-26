import React, { useState, useEffect } from "react";
import { Post } from "../types/Post";
import { JodelService } from "../services/JodelService";
import { Jodel } from "../types/Jodel";
import { useKeycloak } from "@react-keycloak/web";
import { setAuthToken } from "../services/JodelService";
import JodelElement from "./JodelElement";

interface PostCardProps {
  post: Post;
}

const PostElement: React.FC<PostCardProps> = ({ post }) => {
  const { keycloak } = useKeycloak();
  const [showCommentInput, setShowCommentInput] = useState(false);
  const [comment, setComment] = useState("");
  const [jodelList, setJodelList] = useState<Jodel[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (keycloak.authenticated && keycloak.token) {
      setAuthToken(keycloak.token);
    }
  }, [keycloak.authenticated, keycloak.token]);

  useEffect(() => {
    (async () => {
      try {
        const jodelPromises = post.jodelIdList.map((id) =>
          JodelService.fetchJodelById(id)
        );
        const fetchedJodels = await Promise.all(jodelPromises);
        setJodelList(fetchedJodels);
      } catch (err) {
        setError("Failed to load Jodels");
      } finally {
        setLoading(false);
      }
    })();
  }, [post.jodelIdList]);

  const handleCommentClick = () => {
    setShowCommentInput(!showCommentInput);
  };

  const handleCommentChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value);
  };

  const handlePostComment = () => {
    if (comment.trim()) {
      console.log("Comment Posted:", comment);
      const newJodel: Jodel = {
        postId: post.id,
        barbarian: post.barbarian,
        content: comment,
      };
      JodelService.createJodel(newJodel, post.id);
      setComment("");
      setShowCommentInput(false);
    }
  };

  return (
    <div style={styles.card}>
      <div style={styles.id}>ID: {post.id}</div>
      <div style={styles.username}>From: {post.barbarian.username}</div>
      <div style={styles.content}>Content: {post.content}</div>

      {loading && <div style={styles.loading}>Loading jodels...</div>}
      {error && <div style={styles.error}>{error}</div>}

      {jodelList.length > 0 && (
        <div>
          <h3 style={styles.jodelHeader}>Jodels:</h3>
          <ul>
            {jodelList.map((jodel, index) => (
              <JodelElement jodel={jodel} key={index} />
            ))}
          </ul>
        </div>
      )}

      <button onClick={handleCommentClick} style={styles.commentButton}>
        {showCommentInput ? "Cancel" : "Comment"}
      </button>

      {showCommentInput && (
        <div style={styles.commentSection}>
          <input
            type="text"
            value={comment}
            onChange={handleCommentChange}
            placeholder="Write a comment..."
            style={styles.input}
          />
          <button onClick={handlePostComment} style={styles.postButton}>
            Post
          </button>
        </div>
      )}
    </div>
  );
};

const styles = {
  card: {
    border: "1px solid #ccc",
    borderRadius: "8px",
    padding: "16px",
    margin: "12px 0",
    backgroundColor: "#f9f9f9",
    boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.1)",
    maxWidth: "400px",
    fontFamily: "Arial, sans-serif",
  },
  id: {
    fontSize: "12px",
    color: "#555",
    marginBottom: "8px",
  },
  username: {
    fontSize: "18px",
    fontWeight: "bold",
    color: "#333",
    marginBottom: "8px",
  },
  content: {
    fontSize: "16px",
    color: "#444",
    lineHeight: "1.5",
    marginBottom: "12px",
  },
  commentButton: {
    padding: "8px 16px",
    backgroundColor: "#007BFF",
    color: "#fff",
    border: "none",
    borderRadius: "4px",
    cursor: "pointer",
  },
  commentSection: {
    marginTop: "12px",
    display: "flex",
    flexDirection: "column" as const,
    gap: "8px",
  },
  input: {
    padding: "8px",
    fontSize: "14px",
    border: "1px solid #ccc",
    borderRadius: "4px",
  },
  postButton: {
    padding: "8px 16px",
    backgroundColor: "#28a745",
    color: "#fff",
    border: "none",
    borderRadius: "4px",
    cursor: "pointer",
    alignSelf: "flex-start",
  },
  loading: {
    color: "#007BFF",
    fontWeight: "bold",
    marginBottom: "12px",
  },
  error: {
    color: "#FF0000",
    fontWeight: "bold",
    marginBottom: "12px",
  },
  jodelHeader: {
    fontSize: "16px",
    fontWeight: "bold",
    marginBottom: "8px",
  },
  jodelItem: {
    fontSize: "14px",
    color: "#333",
    marginBottom: "4px",
  },
};

export default PostElement;
