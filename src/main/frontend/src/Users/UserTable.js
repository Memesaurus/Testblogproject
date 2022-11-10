import React, { useEffect, useState } from 'react'
import User from './User'
import Table from 'react-bootstrap/Table'

export default function Users() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        fetch('/api/users')
        .then(response => response.json())
        .then(data => setUsers(data))
    }, [])

  return (
    <>
    <Table bordered variant='dark' style={{width: '30%', textAlign: 'center'}}>
        <thead>
            <tr><th colSpan={3}>Users</th></tr>
            <tr>
                <th>Id</th>
                <th>Username</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            {users.map(user => {
                return <User key={user.id} user={user} />
            })}
        </tbody>
    </Table>
    </>
  )
}
