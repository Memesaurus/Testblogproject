import Posts from './Posts/Posts';
import './App.css'
import { useEffect, useState } from 'react';
import OffCanvas from './OffCanvas'
import { Container, Toast, ToastContainer } from 'react-bootstrap';


function App() {
  const [loggedInUser, setLoggedInUser] = useState(null)
  const [currentUserPage, setCurrentUserPage] = useState()
  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const [showError, setShowError] = useState(false)
  const toggleShowError = () => setShowError(!showError)
  const [showLogout, setShowLogout] = useState(false)
  const toggleShowLogout = () => setShowLogout(!showLogout)

  function gotoUserPage(username) {
    setCurrentUserPage(username)
  }

  function onLogoutFormClickHandler() {
    fetch('/api/logout', {
      mode: 'no-cors'
    })
    .then(() => {
      setIsLoggedIn(false)
      setLoggedInUser(undefined)
      toggleShowLogout()
    })
  }

  function onLoginFormClickHandler(data) {
    fetch('/api/login', {
      body: data,
      method: 'POST',
      mode: 'no-cors',
      redirect: 'follow'
    })
    .then(response => {
      if(!response.ok) {
        toggleShowError()
      } 
      else {
        setIsLoggedIn(true)
        setLoggedInUser(data.get('username'))
        setCurrentUserPage(data.get('username'))
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
      onRegisterFormClickHandler={onRegisterFormClickHandler}
      gotoUserPage={() => {
        console.log('unauthorized')
      }} />
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
      <Container>
        Register or log in to view other users' posts or post yourself!
      </Container>
    </>
  )
}
  return (
      <>
        <OffCanvas onLoginFormClickHandler={onLoginFormClickHandler} 
        onRegisterFormClickHandler={onRegisterFormClickHandler} 
        onLogoutFormClickHandler={onLogoutFormClickHandler} 
        isLoggedIn={isLoggedIn}
        gotoUserPage={gotoUserPage}
        loggedInUser={loggedInUser}/>
        <div className='flex-container'>
          <Posts currentUserPage={currentUserPage} loggedInUser={loggedInUser} />
        </div>
      </>
    )
 }
export default App;
