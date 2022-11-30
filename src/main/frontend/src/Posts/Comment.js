import React, { useState } from 'react'
import { Button } from 'react-bootstrap'
import './Comment.css'

export default function Comment({comment, onClickDeletePostHandler, loggedInUser}) {
    const [likes, setLikes] = useState(comment.likeCount)
  
    function handleLike() {
        fetch(`/api/posts/${comment.id}/like`, {
          mode: 'no-cors',
          method: 'POST'
        }).then(response => response.json())
        .then(data => {
          const count = data.data.likeCount
          setLikes(count)
        })
      }

      function handleDeleteButton() {
        if (comment.user.username == loggedInUser) {
          return (
            <div className='deleteBtn'>
            <Button size='sm' variant='danger'
            onClick={() => onClickDeletePostHandler(comment.id)}
            style={{'marginTop': '5px'}}>
              delete
            </Button>
          </div>
          )
        }
      }

  return (
    <div className='commentDiv'>
        {handleDeleteButton()}
        <div className='commentBodyDiv'>
            {comment.body}
        </div>
        <div className='authorDiv'>
            <div className='like'>
                <Button onClick={handleLike}>Like</Button> {likes}
            </div>
            author: {comment.user.username}
        </div>
    </div>
  )
}
