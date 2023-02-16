import React, { useState } from "react";
import { AiFillHeart, AiOutlineHeart } from "react-icons/ai";
import { likeMessage } from "../../api";

export const LikeButton = ({ initLiked, count, id }) => {
  const [isLiked, setIsLiked] = useState(initLiked);
  const setLikedOnPost = () => {
    console.log("like state changed!");
    setIsLiked((current) => !current);
  };

  const onClick = () => {
    likeMessage(id);
    setIsLiked(true);
  };

  const style = {
    width: "1.75rem",
    height: "1.75rem",
  };

  return (
    <>
      {isLiked ? (
        <AiFillHeart onClick={onClick} style={style} />
      ) : (
        <AiOutlineHeart onClick={onClick} style={style} />
      )}{" "}
      {count}
    </>
  );
};
