import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import { useState } from 'react';
import AuthContext from "./context/UserContext";

import Splash from "./components/Splash";
import Login from "./components/Login";
import NavBar from "./components/NavBar";

function App() {

  const [userStatus, setUserStatus] = useState();

  return (
    <Router>
      <AuthContext.Provider value={[userStatus, setUserStatus]}>


        <Switch>
          <Route exact path='/'>
            <Splash />
          </Route>

          <Route exact path='/login'>
            <Login />
          </Route>
        </Switch>

      </AuthContext.Provider>
    </Router>
  )

}

export default App;
