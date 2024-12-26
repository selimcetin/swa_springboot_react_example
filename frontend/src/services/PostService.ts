import axios from "axios";
import { Post } from "../types/Post";

const apiClient = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export const setAuthToken = (token: string | null) => {
  if (token) {
    apiClient.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  } else {
    delete apiClient.defaults.headers.common["Authorization"];
  }
};

export class PostService {
  static async fetchAllPosts(): Promise<Post[]> {
    try {
      const response = await apiClient.get<Post[]>("/posts");
      return response.data;
    } catch (error) {
      PostService.handleError(error);
      throw error;
    }
  }

  static async fetchPostById(id: number): Promise<Post> {
    try {
      const response = await apiClient.get<Post>(`/posts/${id}`);
      return response.data;
    } catch (error) {
      PostService.handleError(error);
      throw error;
    }
  }

  static async createPost(newPost: Omit<Post, "id">): Promise<Post> {
    try {
      const response = await apiClient.post<Post>("/posts", newPost);
      return response.data;
    } catch (error) {
      PostService.handleError(error);
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
