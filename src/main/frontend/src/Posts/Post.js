import React from 'react'
import './Post.css'

export default function Post({post}) {
  return (
    <div className='postDiv'>
        <div className='postBodyDiv'>
            {post.body}
        </div>
        <div className='authorDiv'>
         author: {post.user.username}
        </div>
    </div>
  )
}
