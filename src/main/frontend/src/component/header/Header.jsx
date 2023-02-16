import React from "react";
import { Col, Container, Navbar, NavbarBrand } from "react-bootstrap";
import { WithLogin } from "../WithLogin";
import { AuthorizedButtons } from "./AuthorizedHeaderContent/AuthorizedButtons";
import { OffcanvasUserList } from "./OffcanvasUserList";
import { UnauthorizedButtons } from "./UnauthorizedHeaderContent/UnauthorizedButtons";

export const Header = () => {
  const ContentWithLogin = WithLogin({
    ComponentForAuth: AuthorizedButtons,
    ComponentForNoAuth: UnauthorizedButtons,
  });

  return (
    <Navbar bg="dark" variant="dark">
      <Container fluid>
        <Col xl={2}>
          <NavbarBrand>PostaGram</NavbarBrand>
        </Col>
        <Col>
          <ContentWithLogin />
        </Col>
        <OffcanvasUserList />
      </Container>
    </Navbar>
  );
};
