import React, { useState } from 'react';
import axios from 'axios';

const FreeSearch = () => {
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
        .post('http://localhost:9091/api/validate', address)
        .then((response) => {
          if (response.data.valid) {
            alert('Address is valid!');
          } else {
            alert('Address is invalid!');
          }
        })
        .catch((error) => {
          alert('Error validating address');
        });
    };
  
    return (
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
        <button type="submit">Validate</button>
      </form>
    );
  };
  
  export default FreeSearch;
  