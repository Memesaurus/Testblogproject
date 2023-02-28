import axios from "axios";
import { UserState } from "./redux/userSlice";

export interface PostUser {
  id: number;
  username: string;
  role: {
    id: number;
    authority: string;
  };
}

export interface ApiResponse<T> {
  data: T;
  error: string;
}

export interface LoginData {
  username: string;
  password: string;
}

export interface RegisterData extends LoginData {
  email: string;
}

export interface PostData {
  id?: number;
  username: string;
  body: string;
  user: PostUser;
  likeCount: number;
  comments: CommentData[];
  userLikes: PostUser[];
}

export interface CommentData extends PostData {
  parent: number;
}

export const postLogin = async (data: LoginData): Promise<ApiResponse<PostUser>> => {
  return axios.post("http://localhost:8080/api/login", data).then((response) => response.data);
};

export const logoutCall = async () => {
  return axios.post("http://localhost:8080/api/logout");
};

export const addPost = async (data: PostData): Promise<ApiResponse<PostData>> => {
  return axios
    .post("http://localhost:8080/api/posts", {
      username: data.username,
      body: data.body,
    })
    .then((response) => response.data);
};

export const addComment = async ({
  parent,
  ...data
}: CommentData): Promise<ApiResponse<CommentData>> => {
  return axios
    .post(`http://localhost:8080/api/posts/${parent}/comments`, data)
    .then((response) => response.data);
};

export const getUsers = async (): Promise<ApiResponse<PostUser[]>> => {
  return axios
    .get("http://localhost:8080/api/users");
};

export const registerUser = async (
  data: RegisterData
): Promise<ApiResponse<PostUser>> => {
  return axios
    .post("http://localhost:8080/api/users", data)
    .then((response) => response.data);
};

export const deleteMessage = async (id: number) => {
  axios
    .delete(`http://localhost:8080/api/posts/user/${id}`)
    .then((response) => response.data);
};

export const likeMessage = async (id: number) => {
  axios
    .post(`http://localhost:8080/api/posts/${id}/like`)
    .then((response) => response.data.data);
};

export const getPosts = async (username: string): Promise<ApiResponse<PostData[]>> => {
  return axios
    .get(`http://localhost:8080/api/posts/user/${username}`)
    .then((response) => response.data);
};
