import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/shared/ui/table";
import { Skeleton } from "@/shared/ui/skeleton";
import { Post } from "@/entities/post/types";
import Link from "next/link";

interface PostListProps {
  posts?: Post[];
  isLoading: boolean;
}

export default function PostList(data: PostListProps) {
  if (data.isLoading) {
    <div className="space-y-4">
      {[...Array(5)].map((_, i) => (
        <Skeleton key={i} className="w-full h-10" />
      ))}
    </div>;
  }
  return (
    <Table>
      <TableCaption>게시글 목록</TableCaption>
      <TableHeader>
        <TableRow>
          <TableHead className="w-[100px]">제목</TableHead>
          <TableHead>내용</TableHead>
          <TableHead>작성일</TableHead>
          <TableHead className="text-right">댓글</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {/* test code */}
        <TableRow>
          <TableCell className="font-medium">title</TableCell>
          <TableCell className="max-w-40 overflow-hidden text-ellipsis whitespace-nowrap">
            contentcontentcontentcontentcontent
          </TableCell>
          <TableCell className="md:table-cell">33.33.3</TableCell>
          <TableCell className="text-right">0</TableCell>
        </TableRow>
        {data.posts?.map((post) => (
          <Link
            href={`/board1/${post.id}`}
            className="hover:underline text-blue-600"
          >
            <TableRow>
              <TableCell className="font-medium">{post.title}</TableCell>
              <TableCell className="max-w-40 overflow-hidden text-ellipsis whitespace-nowrap">
                {post.content}
              </TableCell>
              <TableCell className="hidden md:table-cell">
                {new Date().toLocaleDateString()}
              </TableCell>
              <TableCell className="text-right">0</TableCell>
            </TableRow>
          </Link>
        ))}
      </TableBody>
    </Table>
  );
}
