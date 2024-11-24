import { useRouter } from "next/router";
import { useQuery } from "@tanstack/react-query";
import { postApi } from "@/shared/api/post";

export default function PostItem() {
  const router = useRouter();
  const { id } = router.query;

  const { data: post, isLoading } = useQuery({
    queryKey: ["post", id],
    queryFn: () => postApi.getPost({ id: Number(id) }),
    enabled: !!id,
  });

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>{post?.title}</h1>
      <p>{post?.content}</p>
    </div>
  );
}
