import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import axios from 'axios';
import './RestaurantOwner.css';

const RestaurantOwner = () => {
  const [showRestaurantInput, setShowRestaurantInput] = useState(false);
  const [restaurantName, setRestaurantName] = useState('');
  const [restaurantId, setRestaurantId] = useState('');
  const [restaurantDetails, setRestaurantDetails] = useState('');

  // Initialize the history variable
  const history = useHistory();

  const handleCreateMenu = () => {
    // Handle create menu logic
  };

  const handleGetRestaurantDetails = async () => {
    history.push('/restaurant-details');
  };

  const handleAddRestaurant = () => {
    setShowRestaurantInput(true);
  };

  const handleEditRestaurant = () => {
    history.push('/edit-restaurant'); // Navigate to the EditRestaurant component
  };

  const handleCreateRestaurantReport = () => {
    // Handle create restaurant report logic
  };

  const handleRestaurantNameChange = (e) => {
    setRestaurantName(e.target.value);
  };

  const handleAddButtonClick = async () => {
    try {
      const response = await axios.post(`http://localhost:8080/jboss-javaee-webapp/api/RestaurantOwnerControl/AddRestaurant/${restaurantName}`);
      console.log(response.data);
      // Handle success message or additional logic
    } catch (error) {
      console.error('Failed to add restaurant', error);
      // Handle error message or additional logic
    }
  };

  const handleDisplayAllRestaurants = () => {
    // Handle display all restaurants logic
  };

  return (
    <div>
      <h2>Welcome, Restaurant Owner!</h2>
      <h3>Choose what you would like to do:</h3>
      <button onClick={handleCreateMenu}>Create Menu</button>
      <br />
      <button onClick={handleGetRestaurantDetails}>Get Restaurant Details</button>
      <br />
      {!showRestaurantInput && (
        <button onClick={handleAddRestaurant}>Add Restaurant</button>
      )}
      {showRestaurantInput && (
        <div>
          <div>
            <label htmlFor="restaurantName">Restaurant Name:</label>
            <input type="text" id="restaurantName" value={restaurantName} onChange={handleRestaurantNameChange} />
          </div>
          <button onClick={handleAddButtonClick}>Add</button>
        </div>
      )}
      <br />
      <button onClick={handleEditRestaurant}>Edit Restaurant</button>
      <br />
      <button onClick={handleCreateRestaurantReport}>Create Restaurant Report</button>
      <br />
      <button onClick={handleDisplayAllRestaurants}>Display All Restaurants</button>

      {restaurantDetails && (
        <div>
          <h4>Restaurant Details:</h4>
          <pre>{restaurantDetails}</pre>
        </div>
      )}
    </div>
  );
};

export default RestaurantOwner;