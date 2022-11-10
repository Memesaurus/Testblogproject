import React, { useEffect, useRef, useState } from 'react'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import InputGroup from 'react-bootstrap/InputGroup'
import Post from './Post'

export default function Posts({currentUser}) {
    const [posts, setPosts] = useState([])
    const postBodyRef = useRef()
    
    useEffect(() => {
        fetch(`/api/posts/${currentUser}`)
        .then(response => response.json())
        .then(data => setPosts(data.data))
    }, [currentUser])


    function onClickHandler() {
        const postBody = postBodyRef.current.value
        fetch('/api/posts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({body: postBody, username: currentUser})
        })
        .then(response => response.json())
        .then(data => setPosts([...posts, data.data]))
    }
    
    function onClickDangerHandler() {
        let result = window.confirm('Delete confirmation PH')
        if (result) {
            alert('PH')
        }
    }

  return (
    <>
    <div className="d-grid gap-2">
    <Button variant='danger' onClick={onClickDangerHandler}>DELETE POSTS PH</Button>
    </div>
    <InputGroup>
        <Form.Control placeholder='Your input' ref={postBodyRef}></Form.Control>
        <Button onClick={onClickHandler}> Submit </Button>
    </InputGroup>
    {posts.map(post => {
       return <Post key={post.id} post={post} />
    })}
    </>
  )
}
