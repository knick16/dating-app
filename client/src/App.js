import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import { useState } from 'react';
import AuthContext from "./context/UserContext";

import Splash from "./components/Splash";
import Login from "./components/Login";
import Registration from "./components/Registration";
import NavBar from "./components/NavBar";
import NotFound from "./components/NotFound";
import Home from "./components/Home";
import AccountCreated from "./components/AccountCreated";
import DatingProfile from "./components/DatingProfile";

function App() {

  const [userStatus, setUserStatus] = useState();

  return (
    <Router>
      <AuthContext.Provider value={[userStatus, setUserStatus]}>

        <NavBar />

        <Switch>
          <Route exact path='/'>
            {userStatus?.user ? <Redirect to="/home" /> : <Splash />}
          </Route>

          <Route exact path='/login'>
            {userStatus?.user ? <Redirect to="/home" /> : <Login />}
          </Route>

          <Route exact path='/registration'>
            <Registration />
          </Route>

          <Route exact path='/home'>
            {userStatus?.user ? <Home /> : <Redirect to="/" />}
          </Route>

          <Route exact path='/home/welcome'>
            {userStatus?.user ? <AccountCreated /> : <Redirect to="/" />}
          </Route>
          
          <Route exact path='/dating/profile'>
            {userStatus?.user ? <DatingProfile /> : <Redirect to="/" />}
          </Route>

          <Route component={NotFound} />

        </Switch>

      </AuthContext.Provider>
    </Router>
  )

}

export default App;
