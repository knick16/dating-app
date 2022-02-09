import { useContext, useEffect } from 'react';
import { Link } from 'react-router-dom';
import UserContext from "./../context/UserContext";

function Splash() {

    const userManager = useContext(UserContext);

    function checkToken() {
        if (userManager?.currentUser?.exp < Date.now()) {
            userManager.onLogout();
        }
    }

    useEffect(() => {
        let interval = setInterval(() => checkToken(), (1000 * 5))
        return () => clearInterval(interval);
    }, [])

    return (
        <div className='text-center'>
            <h2>Dating App</h2>
            {/* <img src="./Logo.png" /> */}
            <br />
            <Link to="/login" className='btn btn-primary' >Login</Link>
            <a>&emsp;</a>
            <Link to="/registration" className='btn btn-outline-primary' >Register</Link>
        </div>
    )
}

export default Splash;