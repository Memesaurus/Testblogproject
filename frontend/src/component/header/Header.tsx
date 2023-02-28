import React from "react";
import { Col, Container, Navbar, NavbarBrand } from "react-bootstrap";
import WithLogin from "../WithLogin";
import AuthContent from "./AuthContent/AuthContent";
import NoAuthContent from "./NoAuthContent/NoAuthContent";
import OffcanvasUserList from "./OffcanvasUserList";

const Header = () => {
  const WithLoginContent = WithLogin({
    ComponentForAuth: AuthContent,
    ComponentForNoAuth: NoAuthContent,
  });

  return (
    <Navbar bg="dark" variant="dark">
      <Container fluid>
        <Col xl={2}>
          <NavbarBrand>PostaGram</NavbarBrand>
        </Col>
        <Col>
          <WithLoginContent />
        </Col>
        <OffcanvasUserList />
      </Container>
    </Navbar>
  );
};

export default Header;
