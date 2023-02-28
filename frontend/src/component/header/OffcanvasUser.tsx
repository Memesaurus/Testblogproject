import React from 'react'
import { useDispatch } from 'react-redux';
import { setPage } from '../../redux/currentPageSlice';

type Props = {
    name: string;
}

const OffcanvasUser = (props: Props) => {
    const dispatch = useDispatch();
    return (
      <>
        <span onClick={() => dispatch(setPage({ page: props.name }))}> {props.name} </span>
        <p />
      </>
    );
}

export default OffcanvasUser