import React from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/store";
import AuthLogoutButton from "./AuthLogoutButton";

const AuthContent = () => {
  const username: string = useSelector(
    (state: RootState) => state.user.username!
  );

  return (
    <>
      <span style={{ color: "white", verticalAlign: "middle" }}>
        {" "}
        Logged in as {username}
      </span>
      <AuthLogoutButton />
    </>
  );
};

export default AuthContent;
