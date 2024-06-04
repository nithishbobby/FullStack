import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import HomePage from './components/HomePage';
import Login from './components/Login'; // Updated import path
import Registration from './components/Registration'; // Updated import path
import ScanPage from './components/ScanPage'; // Updated import path
import './App.css';

const App = () => {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <div className="container">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Registration />} />
            <Route path="/scan" element={<ScanPage />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
};

export default App;
