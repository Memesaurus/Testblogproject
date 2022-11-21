import React, { useRef, useState } from 'react'
import { Button, Form, FormGroup, Modal, Toast } from 'react-bootstrap';

export default function LoginButton({onLoginFormClickHandler}) {
    const usernameRef = useRef()
    const passwordRef = useRef()
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false)
    const handleShow = () => setShow(true)  

    function handleFetchDataAndPass() {
      const data = new FormData();
      const username = usernameRef.current.value
      const password = passwordRef.current.value
      data.append('username', username)
      data.append('password', password)
      onLoginFormClickHandler(data)
    }

  return (
    <>
      <Button variant="dark" onClick={handleShow}>
        Log in
      </Button>

      <Modal show={show} onHide={handleClose} centered>
        <Modal.Header closeButton>
          <Modal.Title>Log in</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <FormGroup className='mb-3' controlId='formBasicUsername'>
              <Form.Label>Username</Form.Label>
              <Form.Control ref={usernameRef} type='username'/>
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
          <Button variant='primary' onClick={handleFetchDataAndPass}>
            Submit
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  )
}
