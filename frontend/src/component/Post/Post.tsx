import React, { useMemo } from "react";
import { Card, Button } from "react-bootstrap";
import CardHeader from "react-bootstrap/esm/CardHeader";
import { useQueryClient, useMutation } from "react-query";
import { useSelector } from "react-redux";
import { addComment, CommentData, deleteMessage, PostData } from "../../api";
import { RootState } from "../../redux/store";
import WithLogin from "../WithLogin";
import AuthPostInputField from "./AuthPostInputField";
import LikeButton from "./LikeButton";
import NoAuthPostInputField from "./NoAuthPostInputField";

type Props = {
  post: PostData;
  isParent: boolean;
};

const Post = (props: Props) => {
  const TextAreaWithLogin = WithLogin({
    ComponentForAuth: AuthPostInputField,
    ComponentForNoAuth: NoAuthPostInputField,
  });
  const currentUser = useSelector((state: RootState) => state.user);

  const queryClient = useQueryClient();
  const { mutate } = useMutation({
    mutationFn: addComment,
    onSuccess: (newComment) => {
      queryClient.setQueryData(["posts", newComment.data.id], newComment.data);
    },
  });

  const isLikedByUser = (): boolean => {
    const likedUsers = props.post?.userLikes;
    for (let user of likedUsers) {
      if (user.username.localeCompare(currentUser.username!) === 0) {
        return true;
      }
    }
    return false;
  };

  const canDelete = (): boolean => {
    return (
      currentUser.username!.localeCompare(props.post.user.username) === 0 ||
      currentUser.isVeryCool
    );
  };
  
  const handleDelete = () => {
    if(prompt('Confirm deletion?'))
      deleteMessage(props.post.id!);
  }

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
          {props.post.user.username}
          {canDelete() && (
            <Button
              variant="danger"
              size="sm"
              className="float-end"
              onClick={() => handleDelete}
            >
              Delete
            </Button>
          )}
        </CardHeader>

        <Card.Body className="text-center">{props.post.body}</Card.Body>

        <Card.Footer
          style={{
            height: "2.75rem",
          }}
        >
          <LikeButton
            initLiked={isLikedByUser()}
            count={props.post.likeCount}
            id={props.post.id!}
          />
        </Card.Footer>
        {props.isParent && (
          <>
            {props.post.comments.map((comment: CommentData) => (
              <Post key={comment.id} isParent={false} post={comment} />
            ))}
            <TextAreaWithLogin
              placeHolder={"Add comment!"}
              mutate={mutate}
              username={currentUser.username}
              parent={props.post.id}
            />
          </>
        )}
      </Card>
    </>
  );
};

export default Post;
