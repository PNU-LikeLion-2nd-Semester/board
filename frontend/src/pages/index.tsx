import Head from "next/head";
import AppLayout from "../widgets/Layout/app";
import HomeLayout from "../widgets/Layout/home";

export default function HomePage() {
  return (
    <>
      <Head>
        <title>home | penscape</title>
      </Head>
      <AppLayout>
        <HomeLayout />
      </AppLayout>
      {/* <div
        className={`grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20`}
      >
        home page
      </div> */}
    </>
  );
}
