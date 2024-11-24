import { useEffect, useState } from "react";
import { loginApi } from "../api/login";
import { LoginFormSchema } from "@/features/Auth/login/schema";
import { useAuthStore } from "@/features/Auth/login/store";
import { User } from "@/entities/login/types";
import { useRouter } from "next/router";
import { jwtDecode } from "jwt-decode";

interface UseLoginReturn {
  login: (data: LoginFormSchema) => Promise<void>;
  isLoading: boolean;
  error: string | null;
  logout: () => void;
}

interface DecodedToken {
  sub: string;
}

export const useLogin = (): UseLoginReturn => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const setUser = useAuthStore((state) => state.setUser);
  const router = useRouter();

  const getUserFromToken = (token: string): User => {
    const decoded = jwtDecode<DecodedToken>(token);
    return {
      username: decoded.sub,
    };
  };

  useEffect(() => {
    const savedToken = localStorage.getItem("token");

    if (savedToken) {
      try {
        const user = getUserFromToken(savedToken);
        setUser(user);
      } catch (e) {
        console.error("Failed to decode token");
        logout(); // 토큰이 유효하지 않은 경우 로그아웃
      }
    }
  }, [setUser]);

  const login = async (data: LoginFormSchema) => {
    try {
      setIsLoading(true);
      setError(null);

      const response = await loginApi.login(data);
      const token = response.token;

      // test code
      console.log(response);
      const user = getUserFromToken(token);

      localStorage.setItem("token", token);
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
  const logout = () => {
    localStorage.removeItem("token");
    setUser(null);
    router.push("/login");
  };

  return { login, isLoading, error, logout };
};
