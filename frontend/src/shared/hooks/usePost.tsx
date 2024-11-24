import { PostFormSchema } from "@/features/posts/schema";
import { useRouter } from "next/router";
import { useState } from "react";
import { postApi } from "../api/post";

interface UsePostReturn {
  post: (data: PostFormSchema) => Promise<void>;
  isLoading: boolean;
  error: string | null;
}

export const usePost = (): UsePostReturn => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  const post = async (data: PostFormSchema) => {
    try {
      setIsLoading(true);
      setError(null);

      const response = await postApi.uploadPost(data);
      console.log(response);
    } catch (error) {
      setError(
        error instanceof Error
          ? `글을 게시하지 못했습니다 : ${error.message}`
          : "??."
      );
    } finally {
      setIsLoading(false);
    }
  };

  return { post, isLoading, error };
};
