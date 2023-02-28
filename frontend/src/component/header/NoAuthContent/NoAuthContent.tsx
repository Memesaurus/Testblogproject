import React from "react";
import LoginModalButton from "./LoginModalButton";
import RegisterModalButton from "./RegisterModalButton";

const NoAuthContent = () => {
  return (
    <>
      <LoginModalButton />
      <RegisterModalButton />
    </>
  );
};

export default NoAuthContent;
