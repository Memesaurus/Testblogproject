import axios from "axios";

export const postLogin = async (data) => {
  return axios
    .post("http://localhost:8080/api/login", data)
    .then(axios.get("http://localhost:8080/api/users/current"))
    .then((response) => response.data);
};

export const logoutCall = async () => {
  return axios.post("http://localhost:8080/api/logout");
};

export const addPost = async (data) => {
  return axios
    .post("http://localhost:8080/api/posts", {username: data.username, body: data.body})
    .then((response) => response.data.data);
};

export const addComment = async ({parent, ...data}) => {
  return axios
    .post(`http://localhost:8080/api/posts/${parent}/comments`, data)
    .then((response) => response.data.data);
};

export const getUsers = async () => {
  return axios
    .get("http://localhost:8080/api/users")
    .then((response) => response.data);
};

export const deleteMessage = async (id) => {
  axios
    .delete(`http://localhost:8080/api/posts/user/${id}`)
    .then((response) => response.data);
};

export const likeMessage = async (id) => {
  axios
    .post(`http://localhost:8080/api/posts/${id}/like`)
    .then((response) => response.data.data);
};

export const getPosts = async (username) => {
  return axios
    .get(`http://localhost:8080/api/posts/user/${username}`)
    .then((response) => response.data.data);
};
