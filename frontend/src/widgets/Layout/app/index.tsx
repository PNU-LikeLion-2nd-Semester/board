import { ReactNode } from "react";
import Header from "./Header";
import Footer from "./Footer";

export default function AppLayout({ children }: { children: ReactNode }) {
  return (
    <>
      <Header />
      <main>{children}</main>
      <Footer />
    </>
  );
}
