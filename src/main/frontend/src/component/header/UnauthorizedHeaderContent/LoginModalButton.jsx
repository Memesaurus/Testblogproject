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
import { useDispatch } from "react-redux";
import { postLogin } from "../../../api";
import { setPage } from "../../../redux/currentPageSlice";
import { setUser } from "../../../redux/userSlice";

export const LoginModalButton = () => {
  const [isShown, setIsShown] = useState(false);
  const dispatch = useDispatch();
  const { register, handleSubmit } = useForm();

  const onSubmit = async (data) => {
    postLogin(data).then((response) => {
      if (!response?.error) {
        const data = response?.data;
        dispatch(
          setUser({
            username: data.username,
            isVeryCool: data.role.authority === "ROLE_ADMIN" ? true : false,
          })
        );
        dispatch(
          setPage({page: data.username})
        )
      }
    });
  };

  return (
    <>
      <Button variant="dark" onClick={() => setIsShown(true)}>
        Log in
      </Button>

      <Modal show={isShown} centered={true} onHide={() => setIsShown(false)}>
        <ModalHeader closeButton>
          <ModalTitle>Log in</ModalTitle>
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
