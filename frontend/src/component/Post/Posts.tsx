import React from "react";
import { CardGroup, Spinner } from "react-bootstrap";
import { useQueryClient, useMutation, useQuery } from "react-query";
import { useSelector } from "react-redux";
import { addPost, getPosts, PostData } from "../../api";
import { RootState } from "../../redux/store";
import WithLogin from "../WithLogin";
import AuthPostInputField from "./AuthPostInputField";
import NoAuthPostInputField from "./NoAuthPostInputField";
import Post from "./Post";

const Posts = () => {
  const TextAreaWithLogin = WithLogin({
    ComponentForAuth: AuthPostInputField,
    ComponentForNoAuth: NoAuthPostInputField,
  });

  const currentPage = useSelector((state: RootState) => state.page.page);
  const currentUser = useSelector((state: RootState) => state.user);
  const queryClient = useQueryClient();

  const { mutate } = useMutation({
    mutationFn: addPost,
    onSuccess: (newPost: any) =>
      queryClient.setQueryData(["posts", newPost.data.id], newPost.data),
  });

  const { status, data: posts } = useQuery({
    queryKey: [currentPage],
    queryFn: () => getPosts(currentPage),
  });

  const isCurrentUsersPage = (): boolean => {
    return currentUser.username?.localeCompare(currentPage) === 0;
  };

  if (status === "loading") {
    return <Spinner />;
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
          <TextAreaWithLogin
            placeHolder={"Create a Post!"}
            mutate={mutate}
            username={currentUser.username}
          />
        )}
        {posts?.data.map((post: PostData) => {
          return <Post key={post.id} post={post} isParent={true} />;
        })}
      </CardGroup>
    </>
  );
};

export default Posts;
