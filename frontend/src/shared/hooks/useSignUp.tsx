import { useState } from "react";
import { signUpApi } from "../api/signup";
import { SignUpFormSchema } from "@/features/Auth/signup/schema";
import { useAuthStore } from "@/features/Auth/signup/store";
import { User } from "@/entities/signup/types";
import { useRouter } from "next/router";

interface UseSignUpReturn {
  signup: (data: SignUpFormSchema) => Promise<void>;
  isLoading: boolean;
  error: string | null;
}

export const useSignUp = (): UseSignUpReturn => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const setUser = useAuthStore((state) => state.setUser);
  const router = useRouter();

  const signup = async (data: SignUpFormSchema) => {
    try {
      setIsLoading(true);
      setError(null);

      const response = await signUpApi.signup(data);
      // test code
      console.log(response);

      const user: User = {
        username: response.user.username,
      };

      localStorage.setItem("token", response.token);
      setUser(user);

      router.push("./");
    } catch (error) {
      setError(
        error instanceof Error
          ? error.message
          : "회원가입 중 오류가 발생했습니다"
      );
    } finally {
      setIsLoading(false);
    }
  };

  return { signup, isLoading, error };
};
