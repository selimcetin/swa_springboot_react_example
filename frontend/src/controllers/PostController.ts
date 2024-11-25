import axios from "axios";
import { Post } from "../data/classes/Post";

const apiClient = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export class PostController {
  static async fetchPosts(): Promise<Post[]> {
    try {
      const response = await apiClient.get<Post[]>("/posts");
      return response.data;
    } catch (error) {
      PostController.handleError(error);
      throw error;
    }
  }

  static async fetchPostById(id: number): Promise<Post> {
    try {
      const response = await apiClient.get<Post>(`/posts/${id}`);
      return response.data;
    } catch (error) {
      PostController.handleError(error);
      throw error;
    }
  }

  static async createPost(newPost: Omit<Post, "id">): Promise<Post> {
    try {
      const response = await apiClient.post<Post>("/posts", newPost);
      return response.data;
    } catch (error) {
      PostController.handleError(error);
      throw error;
    }
  }

  private static handleError(error: unknown): void {
    if (axios.isAxiosError(error)) {
      console.error(`Axios Error: ${error.message}`, error.response?.data);
    } else {
      console.error("Unexpected Error:", error);
    }
  }
}
