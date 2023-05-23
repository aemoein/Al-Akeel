import React, { useState } from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

const Runner = () => {
  const [orderId, setOrderId] = useState('');
  const [orderStatus, setOrderStatus] = useState('');
  const [completedTrips, setCompletedTrips] = useState(0);
  const history = useHistory();

  const handleOrderIdChange = (e) => {
    setOrderId(e.target.value);
  };

  const handleAcceptOrder = () => {
    // Handle accept order logic
  };

  const handleCancelOrder = () => {
    // Handle cancel order logic
  };

  const handleCompleteOrder = () => {
    // Handle complete order logic
  };

  const handleGetCompletedTrips = () => {
    // Handle get completed trips logic
  };

  const handleGetTripHistory = () => {
    // Handle get trip history logic
  };

  const handleBackButtonClick = () => {
    history.push('/'); // Navigate back to home page
  };

  return (
    <div>
      <h2>Welcome, Runner!</h2>
      <h3>Choose what you would like to do:</h3>
      <div>
        <label htmlFor="orderId">Order ID:</label>
        <input type="text" id="orderId" value={orderId} onChange={handleOrderIdChange} />
      </div>
      <div>
        <button onClick={handleAcceptOrder}>Accept Order</button>
        <button onClick={handleCancelOrder}>Cancel Order</button>
        <button onClick={handleCompleteOrder}>Complete Order</button>
        <button onClick={handleGetCompletedTrips}>Get Completed Trips</button>
        <button onClick={handleGetTripHistory}>Get Trip History</button>
      </div>
      {orderStatus && <p>Order Status: {orderStatus}</p>}
      <p>Completed Trips: {completedTrips}</p>
      <button onClick={handleBackButtonClick}>Back</button>
    </div>
  );
};

export default Runner;