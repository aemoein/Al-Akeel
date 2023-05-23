import React, { useState } from 'react';
import axios from 'axios';
import './SignupForm.css';

const SignupForm = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('');
  const [signupResponse, setSignupResponse] = useState(null); // Store server response

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
      const response = await axios.post(`http://localhost:8080/jboss-javaee-webapp/api/user/signup/${name}/${email}/${password}/${role}`);

      if (response.status === 200) {
        setSignupResponse('Signup successful'); // Store success message
      } else {
        setSignupResponse('Signup failed'); // Store failure message
      }
    } catch (error) {
      console.error('Signup failed', error);
      setSignupResponse('Signup failed'); // Store an error message
    }
  };

  return (
    <div className="form-container">
      {signupResponse ? (
        <>
          <h2>Sign Up</h2>
          <p>{signupResponse}</p>
          {signupResponse !== 'Signup failed' && (
            <button onClick={() => window.location.href = "/login"}>Go to Login</button>
          )}
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
            <select id="role" value={role} onChange={handleRoleChange}>
              <option value="">Select Role</option>
              <option value="Customer">Customer</option>
              <option value="RestaurantOwner">Restaurant Owner</option>
              <option value="Runner">Runner</option>
            </select>
          </div>
          <button type="submit">Sign Up</button>
        </form>
      )}
    </div>
  );
};

export default SignupForm;