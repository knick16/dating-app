import React from 'react';
import { Link } from 'react-router-dom';

const NotFound = () => (
    <div>
        <h1>404</h1>
        <p>Not found...</p>
        <Link to="/" className='btn btn-primary'>Return to splash</Link>
    </div>
);

export default NotFound;