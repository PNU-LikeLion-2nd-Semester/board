import type { LoginRequest, LoginResponse } from "@/entities/login/types";

const API_URL = "http://localhost:8080/api";

export const loginApi = {
  login: async (data: LoginRequest): Promise<LoginResponse> => {
    try {
      const response = await fetch(`${API_URL}/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      return response.json();
    } catch (error) {
      console.error("Login error:", error);
      throw error;
    }
  },
};
