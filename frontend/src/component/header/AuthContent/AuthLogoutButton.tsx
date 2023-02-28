import React from "react";
import { Button } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { logoutCall } from "../../../api";
import { clearUser } from "../../../redux/userSlice";

const AuthLogoutButton = () => {
  const dispatch = useDispatch();

  const handleLogout = () => {
    logoutCall().then(() => dispatch(clearUser));
  };

  return (
    <Button variant="dark" onClick={handleLogout}>
      Logout
    </Button>
  );
};

export default AuthLogoutButton;
