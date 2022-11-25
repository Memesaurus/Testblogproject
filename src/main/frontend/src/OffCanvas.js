import { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Offcanvas from 'react-bootstrap/Offcanvas';
import LoginButton from './OffcanvasButtons/LoginButton';
import LogoutButton from './OffcanvasButtons/LogoutButton';
import RegisterButton from './OffcanvasButtons/RegisterButton';
import User from './Users/User';

export default function OffCanvas({onLoginFormClickHandler, onRegisterFormClickHandler, isLoggedIn, onLogoutFormClickHandler, loggedInUser, gotoUserPage}) {
  const [users, setUsers] = useState([]);

  useEffect(() => {
      fetch('/api/users')
      .then(response => response.json())
      .then(data => setUsers(data))
  }, [])

  function gotoMyPage() {
    gotoUserPage(loggedInUser)
  }

  function handleButtons() {
    if(isLoggedIn) {
      return (
        <>
          <div style={{
            color: 'white',
          }}>
            Logged in as {loggedInUser}!
            <Button variant='dark' onClick={gotoMyPage}>My page</Button>
            <LogoutButton onLogoutFormClickHandler={onLogoutFormClickHandler}>Logout</LogoutButton>
          </div>
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
                return <User key={user.id} user={user} gotoUserPage={gotoUserPage}/>
              })
            }
          </Offcanvas.Body>
        </Navbar.Offcanvas>
        </Container>
      </Navbar>
    </>
    );
  }