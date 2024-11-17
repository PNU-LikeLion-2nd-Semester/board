import {css} from "@emotion/react";

export default function Footer() {
  return <div css={footerCss}>footer</div>;
}

const footerCss = css({
  backgroundColor: "#F2F2F2",
  width: "100%",
  position: "absolute",
  bottom: "0",
});
