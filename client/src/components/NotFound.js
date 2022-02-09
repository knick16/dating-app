import React from 'react';
import { Link } from 'react-router-dom';

const NotFound = () => (
  <div>
    <h1>404 - Not Found!</h1>
    <Link to="/" className='btn btn-primary'>Lets start over (return to splash)</Link>
  </div>
);

export default NotFound;