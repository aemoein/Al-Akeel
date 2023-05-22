import React, { useState } from 'react';
import axios from 'axios';
import './SignupForm.css';

const SignupForm = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('');
  const [isSignedUp, setIsSignedUp] = useState(false); // Track sign up status

  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleRoleChange = (e) => {
    setRole(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Make a POST request to the signup endpoint of your Java EE backend
      await axios.post(`http://localhost:8080/jboss-javaee-webapp/api/user/login/${name}/${email}/${password}/${role}`);
      setIsSignedUp(true);
    } catch (error) {
      console.error('Signup failed', error);
      // Handle signup failure
    }
  };

  return (
    <div className="form-container">
      {isSignedUp ? (
        <>
          <h2>Sign Up</h2>
          <p>Sign up successful! Please log in:</p>
          <a href="/login">Go to Login</a>
        </>
      ) : (
        <form onSubmit={handleSubmit}>
          <h2>Sign Up</h2>
          <div>
            <label htmlFor="name">Name:</label>
            <input type="text" id="name" value={name} onChange={handleNameChange} />
          </div>
          <div>
            <label htmlFor="email">Email:</label>
            <input type="text" id="email" value={email} onChange={handleEmailChange} />
          </div>
          <div>
            <label htmlFor="password">Password:</label>
            <input type="password" id="password" value={password} onChange={handlePasswordChange} />
          </div>
          <div>
            <label htmlFor="role">Role:</label>
            <input type="text" id="role" value={role} onChange={handleRoleChange} />
          </div>
          <button type="submit">Sign Up</button>
        </form>
      )}
    </div>
  );
};

export default SignupForm;