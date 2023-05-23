import React, { useState } from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

const Customer = () => {
  const [orderId, setOrderId] = useState('');
  const [orderStatus, setOrderStatus] = useState('');
  const history = useHistory();

  const handleOrderIdChange = (e) => {
    setOrderId(e.target.value);
  };

  const handleCreateOrder = () => {
    // Handle create order logic
  };

  const handleEditOrder = () => {
    // Handle edit order logic
  };

  const handleCancelOrder = () => {
    // Handle cancel order logic
  };

  const handleListRestaurants = () => {
    // Handle list restaurants logic
  };

  const handleBackButtonClick = () => {
    history.push('/'); // Navigate back to home page
  };

  return (
    <div>
      <h2>Welcome, Customer!</h2>
      <h3>Choose what you would like to do:</h3>
      <div>
        <label htmlFor="orderId">Order ID:</label>
        <input type="text" id="orderId" value={orderId} onChange={handleOrderIdChange} />
      </div>
      <div>
        <button onClick={handleCreateOrder}>Create Order</button>
        <button onClick={handleEditOrder}>Edit Order</button>
        <button onClick={handleCancelOrder}>Cancel Order</button>
        <button onClick={handleListRestaurants}>List Restaurants</button>
      </div>
      {orderStatus && <p>Order Status: {orderStatus}</p>}
      <button onClick={handleBackButtonClick}>Back</button>
    </div>
  );
};

export default Customer;