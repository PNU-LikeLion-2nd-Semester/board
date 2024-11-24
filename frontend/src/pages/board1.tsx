import Head from "next/head";
import AppLayout from "../widgets/Layout/app";
import BoardLayout from "@/widgets/Layout/board";

export default function Board1() {
  return (
    <>
      <Head>
        <title>Board1 | penscape</title>
      </Head>
      <AppLayout>
        <BoardLayout />
      </AppLayout>
    </>
  );
}
