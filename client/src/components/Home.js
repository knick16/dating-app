import { useState, useEffect, useContext } from 'react';
import { useHistory, } from "react-router-dom";
import UserContext from "./../context/UserContext";

function Home() {

    // Initializations.
    const [userStatus] = useContext(UserContext);
    const role = userStatus?.user.authorities;
    const history = useHistory();
    const [conversationList, setConversationList] = useState([]);

    // Function to fetch a user's conversations.
    function getConversationList() {

        fetch(`http://localhost:8080/api/user/conversation`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem("jwt_token")}`
            }
        })

        .then((response) => {
            if (response.status === 200) {
                return response.json();
            } else if (response.status === 403) {
                alert('Session expired.');
                UserContext.setUserStatus(null);
                localStorage.removeItem("token");
                history.push("/");
                PromiseRejectionEvent.reject("Session expired.");
            } else if (response.status === 404) {
                setConversationList([]);
            } else {
                Promise.reject("An error occurred.");
            }
        })

        .then((json) => {
            setConversationList(json)
        });
    };

    // Return JSX.
    return (
        <>
        </>
    )
}

export default Home;