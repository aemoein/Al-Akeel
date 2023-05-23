import React, { useState } from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import './RestaurantOwner.css';

const RestaurantDetails = () => {
  const [restaurantId, setRestaurantId] = useState('');
  const [restaurantDetails, setRestaurantDetails] = useState('');
  const history = useHistory();

  const handleRestaurantIdChange = (e) => {
    setRestaurantId(e.target.value);
  };

  const handleBackButtonClick = () => {
    history.push('/restaurant-owner'); // Navigate back to RestaurantOwner component
  };

  const handleGetRestaurantDetails = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/jboss-javaee-webapp/api/RestaurantOwnerControl/getRestaurantDetails/${restaurantId}`);
      setRestaurantDetails(response.data);
    } catch (error) {
      console.error('Failed to get restaurant details:', error);
    }
  };

  return (
    <div>
      <h2>Restaurant Details</h2>
      <div>
        <label htmlFor="restaurantId">Restaurant ID:</label>
        <input type="text" id="restaurantId" value={restaurantId} onChange={handleRestaurantIdChange} />
        <button onClick={handleGetRestaurantDetails}>Get Details</button>
      </div>
      {restaurantDetails && (
        <div>
          <h3>Restaurant Details:</h3>
          <pre>{restaurantDetails}</pre>
        </div>
      )}
      <button onClick={handleBackButtonClick}>Back</button>
    </div>
  );
};

export default RestaurantDetails;