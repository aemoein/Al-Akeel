import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import axios from 'axios';
import './RestaurantOwner.css';

const EditRestaurant = () => {
  const [choice, setChoice] = useState(0); // 0: No choice, 1: Add meal, 2: Remove meal
  const [mealId, setMealId] = useState('');
  const [mealName, setMealName] = useState('');
  const [mealPrice, setMealPrice] = useState('');
  const [restaurantId, setRestaurantId] = useState('');

  const history = useHistory();

  const handleAddMeal = () => {
    setChoice(1);
    setMealId(0); // Set mealId to 0 when the choice is 1
  };

  const handleRemoveMeal = () => {
    setChoice(2);
  };

  const handleMealIdChange = (e) => {
    setMealId(e.target.value);
  };

  const handleMealNameChange = (e) => {
    setMealName(e.target.value);
  };

  const handleMealPriceChange = (e) => {
    setMealPrice(e.target.value);
  };

  const handleRestaurantIdChange = (e) => {
    setRestaurantId(e.target.value);
  };

  const handleEditButtonClick = async () => {
    try {
      let response;
      if (choice === 1) {
        const url = `http://localhost:8080/jboss-javaee-webapp/api/RestaurantOwnerControl/editRestaurant/${choice}/0/${mealName}/${mealPrice}/${restaurantId}`;
        console.log('Request URL:', url);
        response = await axios.put(url);
        console.log(response.data);
        // Handle success message or additional logic
      } else if (choice === 2) {
        const url = `http://localhost:8080/jboss-javaee-webapp/api/RestaurantOwnerControl/editRestaurant/${choice}/${mealId}/nana/0/${restaurantId}`;
        console.log('Request URL:', url);
        response = await axios.put(url);
        console.log(response.data);
        // Handle success message or additional logic
      } else {
        // Handle error or unexpected choice
      }
    } catch (error) {
      console.error('Failed to edit restaurant', error);
      // Handle error message or additional logic
    }
  };

  const handleBackButtonClick = () => {
    history.push('/restaurant-owner'); // Navigate back to RestaurantOwner component
  };

  return (
    <div>
      <h2>Edit Restaurant</h2>
      <h3>Choose an option:</h3>
      <button onClick={handleAddMeal}>Add a Meal</button>
      <br />
      <button onClick={handleRemoveMeal}>Remove a Meal</button>
      <br />
      {choice === 1 && (
        <div>
          <h3>Add a Meal</h3>
          <div>
            <label htmlFor="mealName">Meal Name:</label>
            <input type="text" id="mealName" value={mealName} onChange={handleMealNameChange} />
          </div>
          <div>
            <label htmlFor="mealPrice">Meal Price:</label>
            <input type="text" id="mealPrice" value={mealPrice} onChange={handleMealPriceChange} />
          </div>
          <div>
            <label htmlFor="restaurantId">Restaurant ID:</label>
            <input type="text" id="restaurantId" value={restaurantId} onChange={handleRestaurantIdChange} />
          </div>
          <button onClick={handleEditButtonClick}>Add</button>
        </div>
      )}
      {choice === 2 && (
        <div>
          <h3>Remove a Meal</h3>
          <div>
            <label htmlFor="mealId">Meal ID:</label>
            <input type="text" id="mealId" value={mealId} onChange={handleMealIdChange} />
          </div>
          <div>
            <label htmlFor="restaurantId">Restaurant ID:</label>
            <input type="text" id="restaurantId" value={restaurantId} onChange={handleRestaurantIdChange} />
          </div>
          <button onClick={handleEditButtonClick}>Remove</button>
        </div>
      )}

        <button onClick={handleBackButtonClick}>Back</button>
    </div>
  );
};

export default EditRestaurant;