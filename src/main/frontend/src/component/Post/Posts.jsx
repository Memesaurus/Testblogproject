import React from "react";
import { CardGroup } from "react-bootstrap";
import { Post } from "./Post";
import { AuthorizedTextAreaSubmit } from "./AuthorizedTextAreaSubmit";
import { WithLogin } from "../WithLogin";
import { UnauthorizedTextAreaSubmit } from "./UnauthorizedTextAreaSubmit";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { addPost, getPosts } from "../../api";
import { useSelector } from "react-redux";

export const Posts = () => {
  const TextAreaWithLogin = WithLogin({
    ComponentForAuth: AuthorizedTextAreaSubmit,
    ComponentForNoAuth: UnauthorizedTextAreaSubmit,
  });

  const currentPage = useSelector((state) => state.page.page);
  const currentUser = useSelector((state) => state.user);
  const queryClient = useQueryClient();

  const { mutate } = useMutation({
    mutationFn: addPost,
    onSuccess: (newPost) =>
      queryClient.setQueryData(["posts", newPost.id], newPost),
  });

  const { status, data: posts } = useQuery({
    queryKey: [currentPage],
    queryFn: () => getPosts(currentPage),
  });

  const isCurrentUsersPage = () => {
    return currentUser.username.localeCompare(currentPage) === 0;
  };

  if (status === "loading") {
    return <h1>Loading...</h1>;
  }

  return (
    <>
      <CardGroup
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          marginTop: "10px",
        }}
      >
        <p className="display-6 text-white">Posts of {currentPage}</p>
        {isCurrentUsersPage() && (
          <TextAreaWithLogin placeHolder={"Create a Post!"} mutate={mutate} username={currentUser.username} />
        )}
        {posts.map((post) => {
          return <Post key={post.id} post={post} isParent={true} />;
        })}
      </CardGroup>
    </>
  );
};
