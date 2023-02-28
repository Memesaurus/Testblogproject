import React from "react";
import { FormControl } from "react-bootstrap";

const NoAuthPostInputField = () => {
  return (
    <FormControl
      disabled
      placeholder="You must be logged in to do this!"
      style={{
        width: "25rem",
      }}
    />
  );
};

export default NoAuthPostInputField;
