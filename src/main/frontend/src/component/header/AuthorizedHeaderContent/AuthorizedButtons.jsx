import React from "react";
import { useSelector } from "react-redux";
import { LogoutButton } from "./LogoutButton";

export const AuthorizedButtons = () => {
  const name = useSelector((state) => state.user.username);

  return (
    <>
      <span style={{ color: "white", verticalAlign: "middle"}}> Logged in as {name}</span>
      <LogoutButton />
    </>
  );
};
