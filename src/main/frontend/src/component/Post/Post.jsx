import React, { useMemo, useState } from "react";
import { Button, Card } from "react-bootstrap";
import CardHeader from "react-bootstrap/esm/CardHeader";
import { WithLogin } from "../WithLogin";
import { AuthorizedTextAreaSubmit } from "./AuthorizedTextAreaSubmit";
import { LikeButton } from "./LikeButton";
import { UnauthorizedTextAreaSubmit } from "./UnauthorizedTextAreaSubmit";
import PropTypes from "prop-types";
import { useSelector } from "react-redux";
import { addComment, deleteMessage } from "../../api";
import { useMutation, useQueryClient } from "react-query";

export const Post = ({ isParent, post }) => {
  const TextAreaWithLogin = WithLogin({
    ComponentForAuth: AuthorizedTextAreaSubmit,
    ComponentForNoAuth: UnauthorizedTextAreaSubmit,
  });
  const currentUser = useSelector((state) => state.user);

  const queryClient = useQueryClient();
  const { mutate } = useMutation({
    mutationFn: addComment,
    onSuccess: (newComment) => {
      queryClient.setQueryData(["posts", newComment.id], newComment);
    }
  });

  const isLikedByUser = useMemo(() => {
    const likedUsers = post?.userLikes;
    for (let user of likedUsers) {
      if (user.username.localeCompare(currentUser.username) === 0) {
        return true;
      }
    }
    return false;
  });

  const checkIfCanDelete = () => {
    return (
      currentUser.username.localeCompare(post.user.username) === 0 ||
      currentUser.isVeryCool
    );
  };

  Post.propTypes = {
    body: PropTypes.string,
    username: PropTypes.string,
  };

  return (
    <>
      <Card
        className="bg-dark text-white"
        style={{
          width: "25rem",
        }}
      >
        <CardHeader
          style={{
            height: "2.75rem",
            fontSize: "large",
          }}
        >
          {post.user.username}
          {checkIfCanDelete() && (
          <Button variant="danger" size="sm" className="float-end" onClick={() => deleteMessage(post.id)}>
              Delete
            </Button>
          )}
        </CardHeader>

        <Card.Body className="text-center">{post.body}</Card.Body>

        <Card.Footer
          style={{
            height: "2.75rem",
          }}
        >
          <LikeButton initLiked={isLikedByUser} count={post.likeCount} id={post.id} />
        </Card.Footer>
        {isParent && (
          <>
            {post.comments.map((comment) => (
              <Post key={comment.id} isParent={false} post={comment}/>
            ))}
            <TextAreaWithLogin placeHolder={"Add comment!"} mutate={mutate} username={currentUser.username} parent={post.id}/>
          </>
        )}
      </Card>
    </>
  );
};
