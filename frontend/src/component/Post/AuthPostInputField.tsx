import React, { useState } from 'react'
import { Button, FormControl } from 'react-bootstrap';
import { UseMutateFunction } from 'react-query';
import autosize from "autosize";

type Props = {
  placeholder: string;
  mutate: any;
  username: string;
  parent: number;
}

const AuthPostInputField = (props: Props) => {
  const [message, setMessage] = useState<string>("");

  const handleMutation = () => {
    props.mutate({
      parent: props.parent,
      username: props.username,
      body: message,
    });
  };

  const onInput = (event: React.FormEvent<HTMLInputElement>) => {
    autosize(event.currentTarget);
    setMessage(event.currentTarget.value);
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
        placeholder={props.placeholder}
      />
      <Button variant="secondary" size="sm" onClick={handleMutation}>
        Submit
      </Button>
    </div>
  );
}

export default AuthPostInputField