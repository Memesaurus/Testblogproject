import React from "react";
import Header from "./component/header/Header";
import Posts from "./component/Post/Posts";

const App = () => {
  return (
    <>
      <div id="toaster_portal" style={{position:'absolute', top:'0', right: '0', zIndex: '10'}}/>
      <Header />
      <Posts />
    </>
  );
};

export default App;