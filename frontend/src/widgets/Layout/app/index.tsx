import {ReactNode} from "react";
import Header from "./Header";
export default function AppLayout({children}: {children: ReactNode}) {
  return (
    <>
      <Header />
      <main>{children}</main>
      <div>_footer</div>
    </>
  );
}
