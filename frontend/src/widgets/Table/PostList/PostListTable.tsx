import PostList from ".";
import { useQuery } from "@tanstack/react-query";
import { postApi } from "@/shared/api/post";
import { useState } from "react";

export default function PostListTable() {
  const [page, setPage] = useState(1);

  const { data, isLoading } = useQuery({
    queryKey: ["posts", page],
    queryFn: () => postApi.getPostList({ page }),
  });
  return <PostList posts={data?.posts} isLoading={isLoading} />;
}
