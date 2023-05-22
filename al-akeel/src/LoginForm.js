import React, { useState } from 'react';
import axios from 'axios';
import './LoginForm.css';

const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loginResponse, setLoginResponse] = useState(null); // Store server response

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    try {
      const response = await axios.post(`http://localhost:8080/jboss-javaee-webapp/api/user/login/${email}/${password}`);
  
      if (response.status === 200) {
        const role = response.data;

        switch (role) {
          case 'Customer':
            setLoginResponse('success');
            window.location.href = '/customer';
            break;
          case 'RestaurantOwner':
            window.location.href = '/restaurant-owner';
            break;
          case 'Runner':
            window.location.href = '/runner';
            break;
          default:
            console.error('Invalid role');
            // Handle invalid role
            break;
        }
      } else {
        setLoginResponse('Login failed'); // Store failure message
      }
    } catch (error) {
      console.error('Login failed', error);
      setLoginResponse('Login failed' + error); // Store an error message
    }
  };  

  return (
    <form className="form-container" onSubmit={handleSubmit}>
      <h2>Login</h2>
      <div>
        <label htmlFor="email">Email:</label>
        <input type="text" id="email" value={email} onChange={handleEmailChange} />
      </div>
      <div>
        <label htmlFor="password">Password:</label>
        <input type="password" id="password" value={password} onChange={handlePasswordChange} />
      </div>
      <button type="submit">Login</button>

      {loginResponse && (
        <div>
          <p>{loginResponse}</p>
          {loginResponse === 'Login successful' && (
            <button onClick={() => window.location.href = "/app"}>Go to App</button>
          )}
        </div>
      )}
    </form>
  );
};

export default LoginForm;