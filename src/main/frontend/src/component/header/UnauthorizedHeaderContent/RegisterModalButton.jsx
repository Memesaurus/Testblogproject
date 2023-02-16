import React, { useState } from "react";
import {
  Button,
  Form,
  FormControl,
  FormGroup,
  FormLabel,
  Modal,
  ModalBody,
  ModalHeader,
  ModalTitle,
} from "react-bootstrap";
import { useForm } from "react-hook-form";

export const RegisterModalButton = () => {
    const [isShown, setIsShown] = useState(false);

    const {
      register,
      handleSubmit
    } = useForm();
  
    const onSubmit = (data) => {
      console.log(`post ${JSON.stringify(data)} to server`);
    };
  
    return (
      <>
        <Button variant="dark" onClick={() => setIsShown(true)}>Register</Button>
  
        <Modal show={isShown} centered={true} onHide={() => setIsShown(false)}>
          <ModalHeader closeButton>
            <ModalTitle>Register</ModalTitle>
          </ModalHeader>
  
          <ModalBody>
            <Form onSubmit={handleSubmit(onSubmit)}>
              <FormGroup>
                <FormLabel>Username</FormLabel>
                <FormControl
                  placeholder="username"
                  {...register("username", { required: true })}
                />
              </FormGroup>
              <FormGroup>
                <FormLabel>Email</FormLabel>
                <FormControl
                  placeholder="email"
                  type="email"
                  {...register("username", { required: true })}
                />
              </FormGroup>
              <FormGroup>
                <FormLabel>Password</FormLabel>
                <FormControl
                  placeholder="password"
                  type="password"
                  {...register("password", { required: true })}
                />
              </FormGroup>
              <p />
              <Button type="submit">Submit</Button>
            </Form>
          </ModalBody>
        </Modal>
      </>
    );
}