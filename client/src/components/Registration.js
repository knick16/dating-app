import { useState, useContext } from 'react';
import { useHistory, Link } from 'react-router-dom';
import jwtDecode from 'jwt-decode';
import UserContext from "./../context/UserContext";

function Registration() {

    // Initializations.
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');

    const [errors, setErrors] = useState([]);
    const userId = 0;
    const disabled = false;
    const roles = [];
    const latitude = null;
    const longitude = null;

    const [_, setUserStatus] = useContext(UserContext);

    const history = useHistory();


    // Input fields.
    const updateUsername = (event) => {
        setUsername(event.target.value);
    };

    const updatePassword = (event) => {
        setPassword(event.target.value);
    };

    const updateConfirmPassword = (event) => {
        setConfirmPassword(event.target.value);
    };

    const updateEmail = (event) => {
        setEmail(event.target.value);
    };

    const updateFirstName = (event) => {
        setFirstName(event.target.value);
    };

    const updateLastName = (event) => {
        setLastName(event.target.value);
    };


    // Submission function.
    const doSubmit = (e) => {
        e.preventDefault();

        if (password !== confirmPassword) {
            return setErrors(["Passwords must match."]);
        }

        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({ userId, username, password, email, firstName, lastName, disabled, roles })
        };

        fetch('http://localhost:8080/api/user/register', init)

            .then(response => {

                // This code executes if the request is successful.
                if (response.status === 201) {
                    doLogin();

                    // This code executes if something went wrong.
                } else {
                    const resp = response.json();
                    console.log(resp);
                    return resp;
                }

                // Error validation.
            }).then((resp) => {
                console.log(resp);
                setErrors(resp);
            }

            ).catch((error) => {
                console.log('An error occurred.');
                console.log(error);
            });
    }


    // Login function.
    const doLogin = async () => {

        const response = await fetch("http://localhost:8080/api/security/authenticate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                username,
                password,
            }),
        });

        // This code executes if the request is successful
        if (response.status === 200) {
            const { jwt_token } = await response.json();
            localStorage.setItem("token", jwt_token);
            setUserStatus({ user: jwtDecode(jwt_token) });
            history.push('/home/welcome');

            // This code executes for a bad request.
        } else if (response.status === 400) {
            const errors = await response.json();
            setErrors(["Account created, but issue logging in."]);

            // This code executes for invalid credentials.
        } else if (response.status === 403) {
            setErrors(["Account created, but issue logging in."]);

            // This code executes for all other errors.
        } else {
            setErrors(["Account created, but issue logging in."]);
        }
    };


    // Return JSX.
    return (
        <div className='text-center'>

            <h2>Account Creation</h2>

            {errors?.length > 0 && (
                <ul className='list-group no-bullet text-danger' align='center'>
                    {errors.map((error, index) => <li key={index}>
                        {error}
                    </li>)}
                </ul>
            )}

            <div className="d-flex h-100">

                <form onSubmit={doSubmit} novalidate>

                    <div className='row' >
                        <label htmlFor='first-name' >Username</label>
                        <input id='first-name' className='form-control' placeholder='First name' type='text' onChange={updateFirstName} required></input>
                    </div>

                    <div className='row' >
                        <label htmlFor='last-name' >Username</label>
                        <input id='last-name' className='form-control' placeholder='Last name' type='text' onChange={updateLastName} required></input>
                    </div>

                    <div className='row' >
                        <label htmlFor='username' >Username</label>
                        <input id='username' className='form-control' placeholder='Username' type='text' onChange={updateUsername} required></input>
                    </div>

                    <div className='row' >
                        <label htmlFor='email-address' >E-mail address</label>
                        <input id='email-address' className='form-control' placeholder='E-mail address' type='text' onChange={updateEmail} required></input>
                    </div>

                    <div className='row' >
                        <label htmlFor='password'>Password</label>
                        <input id='password' className='form-control' type='password' placeholder='At least 8 characters' onChange={updatePassword} required></input>
                    </div>

                    <div className='row' >
                        <label htmlFor='confirm-password'>Confirm password</label>
                        <input id='confirm-password' className='form-control' type='password' placeholder='At least 8 characters' onChange={updateConfirmPassword} required></input>
                    </div>

                    <br />
                    <div className='row' style={{ display: "flex", justifyContent: 'space-between' }} >
                        <button className="btn btn-primary">Create account</button>
                        <button className='btn btn-outline-secondary' onClick={() => history.push('/')}>Cancel</button>
                    </div>
                </form>
            </div>

        </div>
    )
}

export default Registration;