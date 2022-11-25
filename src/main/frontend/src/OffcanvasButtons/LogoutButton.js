import React from 'react'
import { Button } from 'react-bootstrap'

export default function LogoutButton({onLogoutFormClickHandler}) {

  function logoutConfirmationAlert() {
    let result = window.confirm('Logout?')
    if (result) {
      onLogoutFormClickHandler()
    }
  }

  return (
    <Button variant='dark' onClick={logoutConfirmationAlert}>Logout</Button>
  )
}
