import React, { useRef, useState } from 'react'
import { Button, Form, FormGroup, Modal } from 'react-bootstrap';

export default function RegisterButton({onRegisterFormClickHandler}) {
    const usernameRef = useRef()
    const emailRef = useRef()
    const passwordRef = useRef()
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false)
    const handleShow = () => setShow(true)  

    function handleFetchRefsAndPassToRegisterHandler() {
      const username = usernameRef.current.value
      const email = emailRef.current.value
      const password = passwordRef.current.value
      const data = JSON.stringify(
        {
          username: username, 
          email: email, 
          password: password
        }
      )
      onRegisterFormClickHandler(data)
      handleClose()
    }

  return (
    <>
      <Button variant="dark" onClick={handleShow}>
        Register
      </Button>

      <Modal show={show} onHide={handleClose} centered>
        <Modal.Header closeButton>
          <Modal.Title>Register</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <FormGroup className='mb-3' controlId='formBasicUsername'>
              <Form.Label>Username</Form.Label>
              <Form.Control ref={usernameRef} type='username'/>
            </FormGroup>
          </Form>
          <Form>
            <FormGroup className='mb-3' controlId='formBasicEmail'>
              <Form.Label>Email</Form.Label>
              <Form.Control ref={emailRef} type='email' />
            </FormGroup>
          </Form>
          <Form>
            <FormGroup className='mb-3' controlId='formBasicPassword'>
              <Form.Label>Password</Form.Label>
              <Form.Control ref={passwordRef} type='password' />
            </FormGroup>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant='secondary' onClick={handleClose}>
            Close
          </Button>
          <Button variant='primary' onClick={handleFetchRefsAndPassToRegisterHandler}>
            Submit
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  )
}
