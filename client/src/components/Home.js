import { useState, useEffect, useContext } from 'react';
import { useHistory, } from "react-router-dom";
import UserContext from "./../context/UserContext";
import './CustomStyling.css';

function Home() {

    // Initializations.
    const [userStatus, setUserStatus] = useContext(UserContext);
    const history = useHistory();
    const [conversationList, setConversationList] = useState([]);


    // Fetch a user's list of conversations upon page load.
    useEffect(() => {
        getConversationList();
    }, [])


    // Function to fetch a user's conversations.
    function getConversationList() {

        const token = localStorage.getItem("jwt_token");

        console.log(token);

        fetch(`http://localhost:8080/api/user/conversation`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        })

            .then((response) => {
                if (response.status === 200) {
                    return response.json();
                } else if (response.status === 403) {
                    alert('Session expired.');
                    setUserStatus(null);
                    localStorage.removeItem("token");
                    history.push("/");
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
        <div className='text-center'>
            <h2>Messaging</h2>
            <button className='btn btn-block btn-outline-primary' onClick={() => { history.push("/add-conversation") }}>New Conversation +</button>
            {conversationList?.length > 0 ? (
                <ul className='list-group row'>
                    {conversationList.map(conversation => <li className='list-group-item border-0' key={conversation.conversationId}>
                        <button className='btn btn-light btn-block' onClick={() => history.push({ pathname: `/message`, state: { detail: conversation } })}>
                            <div className='font-weight-bold'>{conversation.name}</div>
                            {<ul className='horizontal-list'>
                                {conversation.participants.map(participant => <li className='list-item' key={participant}>
                                    <span>{participant}</span>
                                </li>)}
                            </ul>}
                        </button>
                    </li>)}
                </ul>
            ) : (
                <div>No conversations... yet.</div>
            )}
        </div>
    )
}

export default Home;