import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AddressList = () => {
  const [addresses, setAddresses] = useState([]);

  useEffect(() => {
    axios
      .get('http://localhost:9091/api/addresses') // Replace with your API endpoint
      .then((response) => {
        setAddresses(response.data);
      })
      .catch((error) => {
        console.error('Error fetching addresses:', error);
      });
  }, []);

  return (
    <div>
      <h2>Addresses</h2>
      <ul>
        {addresses.map((address) => (
          <li key={address.id}>
            {address.street}, {address.city}, {address.state} {address.postalCode}, {address.country}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AddressList;
