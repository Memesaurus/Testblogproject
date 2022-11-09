import React from 'react'

export default function User({user}) {
  return (
    <tr>
        <td>{user.id}</td>
        <td>{user.username}</td>
        <td>{user.email}</td>
    </tr>
  )
}
