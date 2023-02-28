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
import { useDispatch } from "react-redux";
import { ApiResponse, LoginData, postLogin, PostUser } from "../../../api";
import { setPage } from "../../../redux/currentPageSlice";
import { setUser } from "../../../redux/userSlice";

const LoginModalButton = () => {
  const [isShown, setIsShown] = useState<boolean>(false);
  const dispatch = useDispatch();
  const { register, handleSubmit } = useForm<LoginData>();

  const onSubmit = async (data: LoginData) => {
    postLogin(data).then((response: ApiResponse<PostUser>) => {
      if (!response.error) {
        dispatch(
          setUser({
            username: response.data.username,
            isVeryCool: response.data.role.authority === "ROLE_ADMIN" ? true : false,
          })
        );
        dispatch(setPage({ page: data.username }));
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

export default LoginModalButton;
