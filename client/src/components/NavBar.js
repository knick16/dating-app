import { Link } from "react-router-dom";
import UserContext from "../context/UserContext";
import { useContext, useEffect } from "react";
import jwtDecode from "jwt-decode";

// Navigation bar for the website.
function NavBar() {

    // Initializations.
    const [userStatus, setUserStatus] = useContext(UserContext);
    const role = userStatus?.user.authorities;


    // Arrow function.
    // Keep user logged-in between browswer sessions.
    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            setUserStatus({ user: jwtDecode(token) });
        }
    }, [setUserStatus]
    );


    // Return JSX.
    return (
        <>
            {role && <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <span className="navbar-brand mb-0 h1">Dating App</span>
                <ul className="navbar-nav mr-auto">
                    <li className="nav-item active">
                        <Link className="nav-link" to="/">Home</Link>
                    </li>
                    <li className="nav-item active">
                        <Link className="nav-link" to="/Dating">Dating</Link>
                    </li>
                    <li className="nav-item active">
                        <Link className="nav-link" to="/Friends">Friends</Link>
                    </li>
                    {role === "ROLE_ADMIN" && <li className="nav-item active">
                        <Link className="nav-link" to="/Users">User Management</Link>
                    </li>
                    }

                </ul>

                <div className="my-2 my-lg-0">
                    <button className="btn btn-outline-primary"
                        onClick={() => {
                            setUserStatus(null);
                            localStorage.removeItem("token");
                        }}
                    >
                        Logout {userStatus.user.sub}
                    </button>
                </div>

            </nav>
            }
        </>
    );
}

export default NavBar;