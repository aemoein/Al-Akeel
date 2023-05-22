import React, { useState } from 'react';
import LoginForm from './LoginForm';
import SignupForm from './SignupForm.js';

const App = () => {
  const [isLogin, setIsLogin] = useState(true);

  const handleOptionChange = (option) => {
    setIsLogin(option === 'login');
  };

  return (
    <div>
      <h1>User Authentication</h1>
      <div>
        <input
          type="radio"
          id="login"
          name="authOption"
          value="login"
          checked={isLogin}
          onChange={() => handleOptionChange('login')}
        />
        <label htmlFor="login">Login</label>

        <input
          type="radio"
          id="signup"
          name="authOption"
          value="signup"
          checked={!isLogin}
          onChange={() => handleOptionChange('signup')}
        />
        <label htmlFor="signup">Sign Up</label>
      </div>
      {isLogin ? <LoginForm /> : <SignupForm />}
    </div>
  );
};

export default App;
