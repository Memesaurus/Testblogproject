import Posts from './Posts/Posts';
import './App.css'
import { useEffect, useState } from 'react';
import OffCanvas from './OffCanvas'
import { Toast, ToastContainer } from 'react-bootstrap';


function App() {
  const [currentUser, setCurrentUser] = useState()
  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const [showError, setShowError] = useState(false)
  const toggleShowError = () => setShowError(!showError)
  const [showLogout, setShowLogout] = useState(false)
  const toggleShowLogout = () => setShowLogout(!showLogout)

  function onLogoutFormClickHandler() {
    fetch('/api/logout')
    setIsLoggedIn(false)
    setCurrentUser(undefined)
    toggleShowLogout()
  }

  function onLoginFormClickHandler(data) {
    fetch('/api/login', {
      body: data,
      method: 'POST'
    })
    .then(response => {
      if(!response.ok) {
        toggleShowError()
      } 
      else {
        console.log(response)
        setIsLoggedIn(true)
        setCurrentUser(data.get('username'))
      }
    })
  }

  function onRegisterFormClickHandler(data) {
    fetch('/api/users', {
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: data
    })
  }

if(!isLoggedIn) {
  return (
    <>
      <OffCanvas 
      onLoginFormClickHandler={onLoginFormClickHandler} 
      onRegisterFormClickHandler={onRegisterFormClickHandler} />
      <ToastContainer position='top-end'>
        <Toast show={showError} onClose={toggleShowError} bg='warning' delay={3000} autohide>
          <Toast.Header>
            Error!
          </Toast.Header>
          <Toast.Body>
            Credentials incorrect!
          </Toast.Body>
        </Toast>
        <Toast show={showLogout} onClose={toggleShowLogout} delay={3000} autohide>
          <Toast.Header>
            Logged out!
          </Toast.Header>
          <Toast.Body>
            You have been logged out!
          </Toast.Body>
        </Toast>
      </ToastContainer>
    </>
  )
}
  return (
      <>
        <OffCanvas onLoginFormClickHandler={onLoginFormClickHandler} 
        onRegisterFormClickHandler={onRegisterFormClickHandler} 
        onLogoutFormClickHandler={onLogoutFormClickHandler} 
        isLoggedIn={isLoggedIn} 
        currentUser={currentUser} />
        <div className='flex-container'>
          <div className='flex-child'>
            <Posts currentUser={currentUser} />
          </div>
        </div>
      </>
    )
 }
export default App;
