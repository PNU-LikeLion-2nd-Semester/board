export interface PostRequest {
  title: string;
  content: string;
  imageFile?: string;
}

export interface PostResponse {
  message: string;
}

export interface GetRequest {
  id: number;
}

// getPost type
export interface Post {
  id: number;
  title: string;
  content: string;
  imagePath?: string;
}

export interface PostListRequest {
  page?: number;
  limit?: number;
}

export interface PostListResponse {
  posts: Post[];
  totalCount: number;
}
