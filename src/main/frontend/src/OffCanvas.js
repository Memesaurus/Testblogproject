import { useEffect, useState } from 'react';
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Offcanvas from 'react-bootstrap/Offcanvas';
import LoginButton from './OffcanvasButtons/LoginButton';
import LogoutButton from './OffcanvasButtons/LogoutButton';
import RegisterButton from './OffcanvasButtons/RegisterButton';
import User from './Users/User';

export default function OffCanvas({onLoginFormClickHandler, onRegisterFormClickHandler, isLoggedIn, onLogoutFormClickHandler, currentUser}) {
  const [users, setUsers] = useState([]);

  useEffect(() => {
      fetch('/api/users')
      .then(response => response.json())
      .then(data => setUsers(data))
  }, [])


  function handleButtons() {
    if(isLoggedIn) {
      return (
        <>
        Logged in as {currentUser}!
        <LogoutButton onLogoutFormClickHandler={onLogoutFormClickHandler}>Logout</LogoutButton>
        </>
      )
    }
    return (
      <>
      <LoginButton onLoginFormClickHandler={onLoginFormClickHandler}>Log in</LoginButton>
      <RegisterButton onRegisterFormClickHandler={onRegisterFormClickHandler} />
      </>
    )
  }

  return (
    <>
      <Navbar variant='dark' bg='dark' expand='false' className='mb-3'>
        <Container fluid>
        <Navbar.Brand>PostaGram</Navbar.Brand>
          <Container>
            {handleButtons()}
          </Container>
        <Navbar.Toggle aria-controls='offcanvasNavbar-expand-false'/>
        <Navbar.Offcanvas id='offcanvasNavbar-expand-false' placement="end">
          <Offcanvas.Header closeButton>
          <Offcanvas.Title id='offcanvasNavbarLabel-expand-flase'>
            Users
          </Offcanvas.Title>
          </Offcanvas.Header>
          <Offcanvas.Body>
            {
              users.map(user => {
                return <User key={user.id} user={user} />
              })
            }
          </Offcanvas.Body>
        </Navbar.Offcanvas>
        </Container>
      </Navbar>
    </>
    );
  }