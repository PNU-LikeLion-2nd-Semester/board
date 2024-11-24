import type {
  PostRequest,
  PostResponse,
  GetRequest,
  Post,
  PostListRequest,
  PostListResponse,
} from "@/entities/post/types";

const API_URL = process.env.NEXT_PUBLIC_BASE_URL;

export const postApi = {
  uploadPost: async (data: PostRequest): Promise<PostResponse> => {
    try {
      const response = await fetch(`${API_URL}/posts`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      return response.json();
    } catch (error) {
      console.error("post error:", error);
      throw error;
    }
  },
  getPostList: async (data: PostListRequest): Promise<PostListResponse> => {
    try {
      const response = await fetch(
        `${API_URL}/posts?page=${data.page}&limit=${data.limit}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      return response.json();
    } catch (error) {
      console.error("get list error:", error);
      throw error;
    }
  },
  getPost: async (data: GetRequest): Promise<Post> => {
    try {
      const response = await fetch(`${API_URL}/posts/${data.id}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });
      return response.json();
    } catch (error) {
      console.error("get post item error:", error);
      throw error;
    }
  },
};
