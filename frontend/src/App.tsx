import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Login from './pages/Login';
import Join from './pages/Join';
import Main from './pages/Main';
import Oauth2Callback from './pages/OAuth2Callback';
import './App.css';

const App = () => {

  
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/join" element={<Join />} />
        <Route path="/main" element={<Main />} />
        <Route path="/oauth2/callback" element={<Oauth2Callback />} />
      </Routes>
    </Router>
  );
}

export default App;
