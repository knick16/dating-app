import { useState, useContext } from 'react';
import { useHistory, Link } from 'react-router-dom';
import jwtDecode from 'jwt-decode';
import UserContext from "./../context/UserContext";
import './CustomStyling.css';

function Login() {

    // Initializations.
    const [_, setUserStatus] = useContext(UserContext);

    const history = useHistory();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const [errors, setErrors] = useState([]);


    // Form input fields.
    const changeUsername = (event) => {
        setUsername(event.target.value);
    };

    const changePassword = (event) => {
        setPassword(event.target.value);
    };

    // Login function.
    const doSubmit = async (e) => {
        e.preventDefault();

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
            history.push("/home");

            // This code executes for a bad request.
        } else if (response.status === 400) {
            const errors = await response.json();
            setErrors(errors);

            // This code executes for invalid credentials.
        } else if (response.status === 403) {
            setErrors(["Invalid credentials."]);

            // This code executes for all other errors.
        } else {
            setErrors(["Unknown error."]);
        }
    };



    // Return JSX.
    return (
        <div className="text-center">
            {/* <div class="fadeIn first">
                <img src="./Logo_image.png" id="icon" alt="User Icon" />
            </div> */}

            {errors.length > 0 && (
                <ul className='list-group no-bullet text-danger' align='center'>
                    {errors.map((error, index) => <li key={index}>
                        {error}
                    </li>)}
                </ul>
            )}

            <div className='form' id="formContent">
                <form className='form' onSubmit={doSubmit} noValidate>
                    <div className='row'>
                        <div className='col' />
                        <div className='col-center'>
                            <label htmlFor="username" >Username:</label>
                            <input id="username" className="form-control" type="text" placeholder='Username' onChange={changeUsername} required />
                        </div>
                        <div className='col' />
                    </div>
                    <div className='row'>
                        <div className='col' />
                        <div className='col-center'>
                            <label htmlFor="password">Password:</label>
                            <input id="password" className="form-control" type="password" placeholder='Password' onChange={changePassword} required />
                        </div>
                        <div className='col' />
                    </div>
                    <br />
                    <span>
                        <button className="btn btn-primary btn-md" >Login</button>
                        <a>&emsp;</a>
                        <Link to='/' className='btn btn-outline-secondary btn-md' >Back</Link>
                    </span>
                </form>
            </div>
        </div>
    )
}

export default Login;