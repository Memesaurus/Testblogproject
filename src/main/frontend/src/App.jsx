import React from 'react';
import './App.css'
import { Header } from './component/header/Header';
import { Posts } from './component/Post/Posts';

function App() {

  return (
    <>
      <div id="modal_container" />
      <Header/>
      <Posts/>
    </>
  );
}

export default App;
