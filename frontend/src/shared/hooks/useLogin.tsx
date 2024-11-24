import { useState } from "react";
import { loginApi } from "../api/login";
import { LoginFormSchema } from "@/features/Auth/login/schema";
import { useAuthStore } from "@/features/Auth/login/store";
import { User } from "@/entities/login/types";
import { useRouter } from "next/router";

interface UseLoginReturn {
  login: (data: LoginFormSchema) => Promise<void>;
  isLoading: boolean;
  error: string | null;
}

export const useLogin = (): UseLoginReturn => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const setUser = useAuthStore((state) => state.setUser);
  const router = useRouter();

  const login = async (data: LoginFormSchema) => {
    try {
      setIsLoading(true);
      setError(null);

      const response = await loginApi.login(data);
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
        error instanceof Error ? error.message : "로그인 중 오류가 발생했습니다"
      );
    } finally {
      setIsLoading(false);
    }
  };

  return { login, isLoading, error };
};
