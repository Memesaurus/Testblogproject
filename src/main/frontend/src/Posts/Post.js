import React, { useRef, useState } from 'react'
import { Button, Form, InputGroup } from 'react-bootstrap'
import './Post.css'
import Comment from './Comment'

export default function Post({ post, onClickDeletePostHandler, loggedInUser}) {
  const [likes, setLikes] = useState(post.likeCount)
  const [comments, setComments] = useState(post.comments)
  const commentBodyRef = useRef()

  function handleDeleteButton() {
    if (post.user.username == loggedInUser) {
      return (
        <div className='deleteBtn'>
        <Button size='sm' variant='danger'
        onClick={() => onClickDeletePostHandler(post.id)}
        style={{'marginTop': '5px'}}>
          delete
        </Button>
      </div>
      )
    }
  }

  function handleLike() {
    fetch(`/api/posts/${post.id}/like`, {
      mode: 'no-cors',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => response.json())
    .then(data => {
      const count = data.data.likeCount
      setLikes(count)
    })
  }

  function onClickAddCommentHandler() {
    const commentBody = commentBodyRef.current.value
    fetch(`/api/posts/${post.id}/comments`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({body: commentBody, username: loggedInUser})
    })
    .then(response => response.json())
    .then(data => setComments([...comments, data.data]))
    commentBodyRef.current.value = null
  }

  return (
    <div className='postDiv'>
        {handleDeleteButton()}
        <div className='postBodyDiv'>
            {post.body}
        </div>
        <div className='authorDiv'>
          <div className='like'>
            <Button onClick={handleLike}>Like</Button> {likes}
          </div>
         author: {post.user.username}
        </div>
        {
          comments.map(comment => (<Comment key={comment.id} comment={comment} onClickDeletePostHandler={onClickDeletePostHandler} loggedInUser={loggedInUser}/>))
        }
        <InputGroup>
          <Form.Control placeholder='add comment' ref={commentBodyRef}></Form.Control>
          <Button onClick={onClickAddCommentHandler}> Submit </Button>
        </InputGroup>
    </div>
  )
}
