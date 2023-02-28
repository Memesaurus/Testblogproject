import React, { useState } from "react";
import {
  Button,
  Offcanvas,
  OffcanvasHeader,
  OffcanvasTitle,
  OffcanvasBody,
  Spinner,
} from "react-bootstrap";
import { AiFillRightSquare } from "react-icons/ai";
import { useQuery } from "react-query";
import { getUsers } from "../../api";
import OffcanvasUser from "./OffcanvasUser";

const OffcanvasUserList = () => {
  const [show, setIsShow] = useState<boolean>(false);

  const { status, data: userResponse } = useQuery({
    queryKey: ["users"],
    queryFn: getUsers,
    staleTime: 10000,
  });
  

  const toggleOffcanvas = (): void => {
    setIsShow((show) => !show);
  };

  return (
    <>
      <Button variant="dark" onClick={toggleOffcanvas}>
        Other users <AiFillRightSquare size={"1.5rem"} />
      </Button>

      <Offcanvas
        className="bg-dark bg-gradient text-white"
        show={show}
        onHide={toggleOffcanvas}
        placement="end"
      >
        <OffcanvasHeader closeButton>
          <OffcanvasTitle>User list</OffcanvasTitle>
        </OffcanvasHeader>
        <OffcanvasBody>
          {status === "loading" ? (
            <Spinner animation="border" />
          ) : (
            userResponse?.data.map((user) => (
              <OffcanvasUser key={user.id} name={user.username} />
            ))
          )}
        </OffcanvasBody>
      </Offcanvas>
    </>
  );
};

export default OffcanvasUserList;
