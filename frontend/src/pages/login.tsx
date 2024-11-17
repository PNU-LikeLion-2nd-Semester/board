import Head from "next/head";
import AppLayout from "../widgets/Layout/app";
import LoginLayout from "@/widgets/Layout/login";

export default function LoginPage() {
  return (
    <>
      <Head>
        <title>Login | penscape</title>
      </Head>
      <AppLayout>
        <LoginLayout />
      </AppLayout>
    </>
  );
}
