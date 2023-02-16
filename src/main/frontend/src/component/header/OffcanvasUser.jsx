import React from "react";
import { useDispatch } from "react-redux";
import { setPage } from "../../redux/currentPageSlice";

export const OffcanvasUser = ({ name }) => {
  const dispatch = useDispatch();
  return (
    <>
      <span onClick={() => dispatch(setPage({ page: name }))}> {name} </span>
      <p />
    </>
  );
};
