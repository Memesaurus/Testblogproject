import React, { useState } from "react";
import { AiFillHeart, AiOutlineHeart } from "react-icons/ai";
import { useSelector } from "react-redux";
import { likeMessage } from "../../api";
import { RootState } from "../../redux/store";
import GeneralUseToast from "../Toasts/GeneralUseToast";

type Props = {
  initLiked: boolean;
  count: number;
  id: number;
};

const LikeButton = (props: Props) => {
  const isLoggedIn = useSelector((state: RootState) => state.user.isLoggedIn);
  const [isLiked, setIsLiked] = useState<boolean>(props.initLiked);
  const [isToast, setIsToast] = useState<boolean>(false);

  const onClick = () => {
    if (isLoggedIn) {
      likeMessage(props.id);
      setIsLiked((current) => !current);
      props.count++;
    } else {
      if (!isToast) {
        setTimeout(() => setIsToast(false), 4000);
      }
      setIsToast(true);
    }
  };

  const style = {
    width: "1.75rem",
    height: "1.75rem",
  };

  return (
    <>
      {isToast && (
        <GeneralUseToast
          isError
          body="You must be logged in to like posts!"
          alertType="warning"
        />
      )}
      {isLiked ? (
        <AiFillHeart onClick={onClick} style={style} />
      ) : (
        <AiOutlineHeart onClick={onClick} style={style} />
      )}{" "}
      {props.count}
    </>
  );
};

export default LikeButton;
