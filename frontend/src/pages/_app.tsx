import "@/app/styles/globals.css";
import type { AppProps } from "next/app";
import Head from "next/head";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient();

export default function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <Head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="description" content="penscape" />
        {/* <link rel="icon" href="/favicon.ico" /> */}
      </Head>
      <QueryClientProvider client={queryClient}>
        <Component {...pageProps} />;
      </QueryClientProvider>
    </>
  );
}
