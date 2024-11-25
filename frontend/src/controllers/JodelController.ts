import axios from "axios";
import { Jodel } from "../data/classes/Jodel";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
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

  private static handleError(error: unknown): void {
    if (axios.isAxiosError(error)) {
      console.error(`Axios Error: ${error.message}`, error.response?.data);
    } else {
      console.error("Unexpected Error:", error);
    }
  }
}
