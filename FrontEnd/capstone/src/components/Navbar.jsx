import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <div className="logo">
          <Link to="/">Vulnerability Identification System</Link>
        </div>
        <div className="nav-links">
          <Link to="/login">Login</Link>
          <Link to="/register">Register</Link>
          <Link to="/scan">Scan</Link>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
