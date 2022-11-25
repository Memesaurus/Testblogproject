import React from 'react'
import { Container } from 'react-bootstrap'

export default function User({user, gotoUserPage}) {

  return (
    <Container onClick={() => gotoUserPage(user.username)}>
      {user.username}
      <p/>
    </Container>
  )
}
