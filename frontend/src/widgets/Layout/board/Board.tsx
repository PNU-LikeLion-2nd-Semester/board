import NewPostForm from "@/widgets/Form/NewPostForm";
import Sidebar from "../home/Sidebar";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/shared/ui/accordion";
import PostListTable from "@/widgets/Table/PostList/PostListTable";

export default function Board() {
  const onSubmit = () => {};
  return (
    <div className="flex flex-col justify-between gap-4 box-border md:flex-row">
      <div className="w-3/4">
        <div className="font-medium ">게시판 이름</div>
        <div></div>
        <div>핫게 ... </div>
        <Accordion type="single" collapsible>
          <AccordionItem value="item-1">
            <AccordionTrigger>새 글을 작성해 주세요</AccordionTrigger>
            <AccordionContent onSubmit={onSubmit}>
              <NewPostForm />
            </AccordionContent>
          </AccordionItem>
        </Accordion>
        <PostListTable />
        <div className="flex flex-row justify-between">
          <div>search bar</div>
          <div>pagenation</div>
        </div>
      </div>
      <Sidebar />
    </div>
  );
}
