import autosize from "autosize";
import React, { useState } from "react";
import { Button, FormControl } from "react-bootstrap";

export const AuthorizedTextAreaSubmit = ({
  placeHolder,
  mutate,
  username,
  parent,
}) => {
  const [message, setMessage] = useState("");

  const handleMutation = () => {
    mutate({
      parent: parent,
      username: username,
      body: message,
    });
  };

  const onInput = (event) => {
    autosize(event.target);
    setMessage(event.target.value);
  };

  return (
    <div
      style={{
        display: "flex",
        width: "25rem",
      }}
    >
      <FormControl
        as="textarea"
        rows={1}
        maxLength={200}
        style={{ resize: "none" }}
        onInput={onInput}
        placeholder={placeHolder}
      />
      <Button variant="secondary" size="sm" onClick={handleMutation}>
        Submit
      </Button>
    </div>
  );
};
