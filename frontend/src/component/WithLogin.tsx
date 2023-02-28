import React, { FC } from 'react'
import { useSelector } from "react-redux"
import { RootState } from '../redux/store';

type Props = {
    ComponentForAuth: FC<any>;
    ComponentForNoAuth: FC<any>;
}

const WithLogin = ({ComponentForAuth, ComponentForNoAuth}: Props) => {

  const WithLoginWrapper = (props: any) => {
    const isLoggedIn : boolean = useSelector((state: RootState) => state.user.isLoggedIn)

    if (isLoggedIn) {
        return <ComponentForAuth {...props} />;
    }
    return <ComponentForNoAuth {...props} />;
  };

  return WithLoginWrapper;
}

export default WithLogin