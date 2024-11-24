import type { SignUpRequest, SignUpResponse } from "@/entities/signup/types";

const API_URL = "https://a4f6-121-145-132-152.ngrok-free.app/api";

export const signUpApi = {
  signup: async (data: SignUpRequest): Promise<SignUpResponse> => {
    try {
      const response = await fetch(`${API_URL}/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      return response.json();
    } catch (error) {
      console.error("Sign up error:", error);
      throw error;
    }
  },
};
