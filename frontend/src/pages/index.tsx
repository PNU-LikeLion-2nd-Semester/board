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
    </>
  );
}
