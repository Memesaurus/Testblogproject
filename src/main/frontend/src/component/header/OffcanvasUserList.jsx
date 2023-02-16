import React, { useState } from "react";
import { Button, Offcanvas, OffcanvasBody, OffcanvasHeader, OffcanvasTitle } from "react-bootstrap";
import { AiFillRightSquare } from "react-icons/ai";
import { useQuery } from "react-query";
import { getUsers } from "../../api";
import { OffcanvasUser } from "./OffcanvasUser";

export const OffcanvasUserList = () => {
    const [show, setIsShow] = useState(false);

    const {
      status,
      data: users,
    } = useQuery({
      queryKey: ['users'],
      queryFn: getUsers,
      staleTime: 10000,
    })

    const toggleOffcanvas = () => {
      setIsShow(show => !show);
    };

  return (
    <>
      <Button variant="dark" onClick={toggleOffcanvas}>
        Other users <AiFillRightSquare size={"1.5rem"} />
      </Button>

      <Offcanvas className="bg-dark bg-gradient text-white" show={show} onHide={toggleOffcanvas} placement="end">
        <OffcanvasHeader closeButton>
          <OffcanvasTitle>User list</OffcanvasTitle>
        </OffcanvasHeader>
        <OffcanvasBody>
          { status === "loading" ? 
          <span>Loading...</span> :
          users?.map(user => <OffcanvasUser key={user.id} name={user.username}/>)
          }
        </OffcanvasBody>
      </Offcanvas>
    </>
  );
};
