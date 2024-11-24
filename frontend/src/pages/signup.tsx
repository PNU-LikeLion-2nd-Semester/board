import Head from "next/head";
import AppLayout from "../widgets/Layout/app";
import SignUpLayout from "@/widgets/Layout/signup";

export default function SignUpPage() {
  return (
    <>
      <Head>
        <title>Sign Up | penscape</title>
      </Head>
      <AppLayout>
        <SignUpLayout />
      </AppLayout>
    </>
  );
}
