import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import LoginForm from './LoginForm';
import SignupForm from './SignupForm';
import Customer from './Customer';
import RestaurantOwner from './RestaurantOwner';
import Runner from './Runner';
import EditRestaurant from './EditRestaurant';
import RestaurantDetails from './RestaurantDetails';

const App = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [userRole, setUserRole] = useState('');

  const handleOptionChange = (option) => {
    setIsLogin(option === 'login');
  };

  const handleUserRole = (role) => {
    setUserRole(role);
  };

  return (
    <div>
      <h1>Al-Akeel</h1>
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
      {isLogin ? (
        <LoginForm handleUserRole={handleUserRole} />
      ) : (
        <SignupForm handleUserRole={handleUserRole} />
      )}
      {/* Remove the following lines
      {userRole === 'customer' && <Customer />}
      {userRole === 'restaurant_owner' && <RestaurantOwner />}
      {userRole === 'runner' && <Runner />} */}
    </div>
  );
};

const Root = () => {
  return (
    <Router>
      <Switch>
        {/* Add the following routes */}
        <Route path="/customer" component={Customer} />
        <Route path="/restaurant-owner" component={RestaurantOwner} />
        <Route path="/runner" component={Runner} />
        <Route path="/edit-restaurant" component={EditRestaurant} />
        <Route path="/restaurant-details" component={RestaurantDetails} />
        <Route path="/" component={App} />
      </Switch>
    </Router>
  );
};

export default Root;