import React from 'react';
import { Link } from 'react-router-dom';
import './HomePage.css';

const HomePage = () => {
  return (
    <div className="home-page">
      <header>
        <h1>Welcome to the Vulnerability Identification  System</h1>
        <p>Your one-stop solution for comprehensive code security analysis and best practices adherence.</p>
      </header>
      <main>
        <section>
          <h2>Our Services</h2>
          <p>We offer a variety of scanning services to ensure your code is secure and follows the best coding practices:</p>
          <ul>
            <li><strong>Static Code Analysis:</strong> Scan your code for potential bugs, vulnerabilities, and violations of coding standards.</li>
          </ul>
          <div className="scan-link">
            <Link to="/scan">Go to Scan Page</Link>
          </div>
        </section>
      </main>
      <nav>
        <ul>
          <li><Link to="/login">Login</Link></li>
          <li><Link to="/register">Register</Link></li>
        </ul>
      </nav>
    </div>
  );
}

export default HomePage;
