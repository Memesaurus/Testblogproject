import React, { useState } from "react";
import {
  Button,
  Modal,
  ModalHeader,
  ModalTitle,
  ModalBody,
  Form,
  FormGroup,
  FormLabel,
  FormControl,
} from "react-bootstrap";
import { useForm } from "react-hook-form";
import { RegisterData, registerUser } from "../../../api";
import GeneralUseToast from "../../Toasts/GeneralUseToast";

const RegisterModalButton = () => {
  const [isShown, setIsShown] = useState<boolean>(false);
  const [isToast, setIsToast] = useState<boolean>(false);

  const { register, handleSubmit } = useForm<RegisterData>();

  const onSubmit = (data: RegisterData) => {
    registerUser(data).then((response) => {
      if (!response.error) {
        if (!isToast) {
          setTimeout(() => setIsToast(false), 4000);
        }
        setIsToast(true);
      }
    });
  };

  return (
    <>
      {isToast && (
        <GeneralUseToast alertType={"dark"} body={"registration success!"} />
      )}
      <Button variant="dark" onClick={() => setIsShown(true)}>
        Register
      </Button>

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
                {...register("email", { required: true })}
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
};

export default RegisterModalButton;
