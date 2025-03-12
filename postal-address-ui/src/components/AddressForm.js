import React, { useState } from 'react';
import axios from 'axios';

const AddressForm = () => {
  const [address, setAddress] = useState({
    street: '',
    city: '',
    state: '',
    postalCode: '',
    country: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setAddress((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post('http://localhost:9091/api/addresses', address) // Replace with your API endpoint
      .then((response) => {
        alert('Address added successfully!');
      })
      .catch((error) => {
        alert('Error adding address');
      });
  };

  return (
    <div>
      <h2>Add Address</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="street"
          value={address.street}
          onChange={handleChange}
          placeholder="Street"
        />
        <input
          type="text"
          name="city"
          value={address.city}
          onChange={handleChange}
          placeholder="City"
        />
        <input
          type="text"
          name="state"
          value={address.state}
          onChange={handleChange}
          placeholder="State"
        />
        <input
          type="text"
          name="postalCode"
          value={address.postalCode}
          onChange={handleChange}
          placeholder="Postal Code"
        />
        <input
          type="text"
          name="country"
          value={address.country}
          onChange={handleChange}
          placeholder="Country"
        />
        <button type="submit">Add Address</button>
      </form>
    </div>
  );
};

export default AddressForm;
