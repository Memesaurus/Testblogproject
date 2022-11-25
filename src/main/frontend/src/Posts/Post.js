import React, { useState } from 'react'
import { Button } from 'react-bootstrap'
import './Post.css'

export default function Post({post, onClickDeletePostHandler, loggedInUser}) {
  const [likes, setLikes] = useState(post.likeCount);

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
      method: 'POST'
    }).then(response => response.json())
    .then(data => {
      const count = data.data.likeCount
      setLikes(count)
    })
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
    </div>
  )
}
