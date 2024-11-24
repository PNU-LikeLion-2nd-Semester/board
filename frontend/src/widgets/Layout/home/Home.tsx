import Profile from "./Profile";
import Sidebar from "./Sidebar";

export default function Home() {
  return (
    <div className="flex flex-col justify-between gap-4 box-border md:flex-row">
      <Profile />
      <div>인기 게시판</div>
      <Sidebar />
    </div>
  );
}
