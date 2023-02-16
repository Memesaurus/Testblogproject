import React from "react";
import { useSelector } from "react-redux";

export const WithLogin = ({ ComponentForAuth, ComponentForNoAuth }) => {
  const WrappedComponentWithLogin = (props) => {
    const isLoggedIn = useSelector((state) => state.user.isLoggedIn);
    if (isLoggedIn) {
      return <ComponentForAuth {...props} />;
    }
    return <ComponentForNoAuth {...props} />;
  };
  return WrappedComponentWithLogin;
};
