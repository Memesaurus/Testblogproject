import React, { useEffect, useRef, useState } from 'react'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import InputGroup from 'react-bootstrap/InputGroup'
import Post from './Post'
import './Posts.css'

export default function Posts({currentUserPage, loggedInUser}) {
    const [posts, setPosts] = useState([])
    const postBodyRef = useRef()

    useEffect(() => {
        fetch(`/api/posts/user/${currentUserPage}`)
            .then(response => response.json())
            .then(data => setPosts(data.data))
    }, [currentUserPage])

    function onClickHandler() {
        const postBody = postBodyRef.current.value
        fetch('/api/posts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({body: postBody, username: loggedInUser})
        })
        .then(response => response.json())
        .then(data => setPosts([...posts, data.data]))
        .then(postBodyRef.current.value = null)
    }

    function deletePost(postId) {
        fetch(`/api/posts/user/${postId}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            if (data.error != null) {
                console.log(data.error)
            }
            else {
                console.log(data.data)
                setPosts(posts.filter(post => post.id != postId))
            }
        })
    }

    function onClickDeletePostHandler(id) {
        let result = window.confirm('Delete confirmation PH')
        postBodyRef.current.value = null
        if (result) {
            deletePost(id)
        }
    }

    function handleInputAppearance() {
        if (currentUserPage === loggedInUser) {
            return (
                <InputGroup>
                    <Form.Control placeholder='Your input' ref={postBodyRef}></Form.Control>
                    <Button onClick={onClickHandler}> Submit </Button>
                </InputGroup>
            )
        }   
    }

  return (
    <>
        <div className='postsBody'>
            Posts of {currentUserPage}
            {handleInputAppearance()}
            {
                posts.map(post => (<Post key={post.id} post={post} onClickDeletePostHandler={onClickDeletePostHandler} currentUserPage={currentUserPage} loggedInUser={loggedInUser}/>))
            }
        </div>
    </>
  )
}``