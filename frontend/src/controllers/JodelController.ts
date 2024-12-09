import axios from "axios";
import { Jodel } from "../data/classes/Jodel";

const apiClient = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export class JodelController {
  static async fetchAllJodels(): Promise<Jodel[]> {
    try {
      const response = await apiClient.get<Jodel[]>("/jodels");
      return response.data;
    } catch (error) {
      JodelController.handleError(error);
      throw error;
    }
  }

  static async fetchJodelById(id: number): Promise<Jodel> {
    try {
      const response = await apiClient.get<Jodel>(`/jodels/${id}`);
      return response.data;
    } catch (error) {
      JodelController.handleError(error);
      throw error;
    }
  }

  static async createJodel(newJodel: Omit<Jodel, "id">): Promise<Jodel> {
    try {
      const response = await apiClient.post<Jodel>("/jodels", newJodel);
      return response.data;
    } catch (error) {
      JodelController.handleError(error);
      throw error;
    }
  }

  static async updateJodel(
    id: number,
    updatedJodel: Partial<Jodel>
  ): Promise<Jodel> {
    try {
      const response = await apiClient.put<Jodel>(
        `/jodels/${id}`,
        updatedJodel
      );
      return response.data;
    } catch (error) {
      JodelController.handleError(error);
      throw error;
    }
  }

  static async deleteJodel(id: number): Promise<void> {
    try {
      await apiClient.delete(`/jodels/${id}`);
    } catch (error) {
      JodelController.handleError(error);
      throw error;
    }
  }

  static async createJodelOnPost(
    newJodel: Omit<Jodel, "id">,
    postId: number
  ): Promise<Jodel> {
    try {
      const response = await apiClient.post<Jodel>(
        `/jodels/post?postId=${postId}`,
        newJodel
      );
      return response.data;
    } catch (error) {
      JodelController.handleError(error);
      throw error;
    }
  }

  static async createJodelOnJodel(
    newJodel: Omit<Jodel, "id">,
    jodelId: number
  ): Promise<Jodel> {
    try {
      const response = await apiClient.post<Jodel>(
        `/jodels/jodel?jodelId=${jodelId}`,
        newJodel
      );
      return response.data;
    } catch (error) {
      JodelController.handleError(error);
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
