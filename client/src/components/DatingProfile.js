import { useState, useEffect, useContext } from 'react';
import { useHistory, } from "react-router-dom";
import UserContext from "./../context/UserContext";

function DatingProfile() {

    // Initializations.
    const [userStatus, setUserStatus] = useContext(UserContext);
    const [errors, setErrors] = useState([]);
    const history = useHistory();

    const [age, setAge] = useState(0);
    const [userGender, setUserGender] = useState('');
    const [preferredGender, setPreferredGender] = useState('');
    const [travelRadius, setTravelRadius] = useState(0);

    const userId = userStatus.user.userId;


    // Input fields.
    const updateAge = (event) => {
        setAge(event.target.value);
    };

    const updateUserGender = (event) => {
        setUserGender(event.target.value);
    };

    const updatePreferredGender = (event) => {
        setPreferredGender(event.target.value);
    };

    const updateTravelRadius = (event) => {
        setTravelRadius(event.target.value);
    };


    // Submission function.
    const doAdd = (e) => {
        e.preventDefault();
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify({ userId, age, userGender, preferredGender, travelRadius })
        };

        fetch('http://localhost:8080/api/user/profile/preferences/add', init)

            .then(response => {

                // This code executes if the request is successful.
                if (response.status === 201) {
                    history.push('/home');

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


    // Return JSX.
    return (

        <div className='text-center'>

            <h2>Dating Profile</h2>

            {errors?.length > 0 && (
                <ul className='list-group no-bullet text-danger' align='center'>
                    {errors.map((error, index) => <li key={index}>
                        {error}
                    </li>)}
                </ul>
            )}

            <div className="d-flex h-100">

                <form onSubmit={doAdd} novalidate>

                    <div className='row' >
                        <label htmlFor='user-age' >Your age</label>
                        <input id='user-age' className='form-control' placeholder='Age' type='number' onChange={updateAge} required min='18' max='100'></input>
                    </div>

                    <div class='row'>
                        <label htmlFor='user-gender'>Your gender</label>
                        <select className="custom-select mr-sm-2" id="user-gender" onChange={updateUserGender} required>
                            <option selected>Choose...</option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                            <option value="non-binary">Non-binary</option>
                        </select>
                    </div>

                    <div class='row'>
                        <label htmlFor='preferred-gender'>Your preferred gender</label>
                        <select className="custom-select mr-sm-2" id="preferred-gender" onChange={updatePreferredGender} required>
                            <option selected>Choose...</option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                            <option value="none">None</option>
                        </select>
                    </div>

                    <div className='row' >
                        <label htmlFor='search-radius' >Search radius</label>
                        <input id='search-radius' className='form-control' placeholder='Distance in miles' type='number' onChange={updateTravelRadius} required min='1' max='100'></input>
                    </div>

                    <br />
                    <div className='row' style={{ display: "flex", justifyContent: 'space-between' }} >
                        <button className="btn btn-primary">Submit</button>
                        <button className='btn btn-outline-secondary' onClick={() => history.push('/home')}>Cancel</button>
                    </div>
                </form>
            </div>

        </div>








    )

}

export default DatingProfile;