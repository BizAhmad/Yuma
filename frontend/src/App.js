import React, { Component } from 'react';
import logo from './yuma.svg';
import './App.css';

import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import LoginCaterer from './LoginCaterer';
import Registration from './Registration';

class App extends Component {
  render() {
    return (
       <Router>
        <div className="App">
          <div className="App-header">
            <img src={logo} className="App-logo" alt="logo" />
            <h2>Welcome to Yuma Ops!</h2>
          </div>
          
          <Link className="navbutton" to="/">Home</Link>
          <Link className="navbutton" to="/LoginCaterer">Login</Link>
          <Link className="navbutton" to="/Registration">Registration</Link>
          <br></br>
          
          
          <Route exact path="/LoginCaterer" component={LoginCaterer} />
          <Route exact path="/Registration" component={Registration} />
          
          
          
        </div>
       </Router>
    );
  }
}

export default App;
