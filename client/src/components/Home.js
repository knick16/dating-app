import { useState, useEffect, useContext } from 'react';
import { useHistory, } from "react-router-dom";
import UserContext from "./../context/UserContext";

function Home() {

    // Initializations.
    const [userStatus] = useContext(UserContext);
    const role = userStatus?.user.authorities;
    const history = useHistory();

    // Return JSX.
    return (
        <>
                <h2>User  {userStatus?.user.sub} successfully logged in!</h2>
        </>
    )
}

export default Home;