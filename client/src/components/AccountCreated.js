import { useState, useContext } from 'react';
import { useHistory, Link } from 'react-router-dom';
import UserContext from "./../context/UserContext";

function AccountCreated() {

    // Initializations
    const [userStatus, setUserStatus] = useContext(UserContext);

    const history = useHistory();


    // Return JSX.
    return (
        <>
            <p>
                Congratulations, your account was successfully created!
            </p>
            <p>
                The next step is to {' '}
                <Link to='/profile/dating' className='text-primary' >create your dating profile</Link>
                .
            </p>
            <p>
                Or, {' '}
                <Link to='/home' className='text-primary' >skip for now</Link>
                .
            </p>

        </>
    )
}

export default AccountCreated;