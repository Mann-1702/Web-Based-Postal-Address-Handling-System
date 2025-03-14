import React, { useState, useEffect } from 'react';
import axios from 'axios';

const FreeSearch = () => {
  const [address, setAddress] = useState({
    address01: '',
    address02: '',
    postalCode: '',
    city: '',
    state: '',
    country: '',
  });

  const [countries, setCountries] = useState([]);
  const [countryId, setCountryId] = useState('');
  const [stateId, setStateId] = useState('');
  const [cityId, setCityId] = useState('');
  const [addresses, setAddresses] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    // Fetch countries
    axios.get('http://localhost:9091/api/v1/location/countries')
      .then((response) => {
        setCountries(response.data);
      })
      .catch((error) => {
        console.error('Error fetching countries:', error);
      });
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setAddress((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    let selectedCountryId = '';
    let selectedStateId = '';
    let selectedCityId = '';

    // Fetch countryId if country is provided
    if (address.country) {
      const selectedCountry = countries.find((c) => c.name === address.country);
      if (selectedCountry) {
        selectedCountryId = selectedCountry.id;
        setCountryId(selectedCountryId);

        // Fetch states based on countryId if state is provided
        if (address.state) {
          try {
            const statesResponse = await axios.get(`http://localhost:9091/api/v1/location/countries/${selectedCountryId}/states`);
            const selectedState = statesResponse.data.find((s) => s.name === address.state);
            if (selectedState) {
              selectedStateId = selectedState.id;
              setStateId(selectedStateId);

              // Fetch cities based on stateId if city is provided
              if (address.city) {
                try {
                  const citiesResponse = await axios.get(`http://localhost:9091/api/v1/location/states/${selectedStateId}/cities`);
                  const selectedCity = citiesResponse.data.find((c) => c.name === address.city);
                  if (selectedCity) {
                    selectedCityId = selectedCity.id;
                    setCityId(selectedCityId);
                  }
                } catch (error) {
                  console.error('Error fetching cities:', error);
                }
              }
            }
          } catch (error) {
            console.error('Error fetching states:', error);
          }
        }
      }
    }

    // Construct the request body with all fields, even if they are blank
    const requestBody = {
      address01: address.address01 || '',
      address02: address.address02 || '',
      postalCode: address.postalCode || '',
      countryId: selectedCountryId || '',
      stateId: selectedStateId || '',
      cityId: selectedCityId || '',
    };

    // Call the new API with the constructed request body
    try {
      const response = await axios.post('http://localhost:9091/api/v1/addresses/search', requestBody);
      console.log('API Response:', response.data); // Log the API response
      setAddresses(response.data.content);
      setCurrentPage(response.data.pageable.pageNumber);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error('Error validating address:', error);
      alert('Error validating address');
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      setCurrentPage(currentPage + 1);
      fetchPage(currentPage + 1);
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      setCurrentPage(currentPage - 1);
      fetchPage(currentPage - 1);
    }
  };

  const fetchPage = async (page) => {
    try {
      const requestBody = {
        address01: address.address01 || '',
        address02: address.address02 || '',
        postalCode: address.postalCode || '',
        countryId: countryId || '',
        stateId: stateId || '',
        cityId: cityId || '',
        page: page,
      };
      const response = await axios.post('http://localhost:9091/api/v1/addresses/search', requestBody);
      setAddresses(response.data.content);
      setCurrentPage(response.data.pageable.pageNumber);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error('Error fetching page:', error);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="address01"
          value={address.address01}
          onChange={handleChange}
          placeholder="Address Line 1"
        />
        <input
          type="text"
          name="address02"
          value={address.address02}
          onChange={handleChange}
          placeholder="Address Line 2"
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
          name="country"
          value={address.country}
          onChange={handleChange}
          placeholder="Country"
        />
        <button type="submit">Validate</button>
      </form>

      <div>
        <h2>Addresses</h2>
        <ul>
          {addresses.map((addr) => (
            <li key={addr.id}>
              {addr.address01}, {addr.address02}, {addr.postalCode}, {addr.cityName}, {addr.stateName}, {addr.CounrtyName}
            </li>
          ))}
        </ul>
        <button onClick={handlePreviousPage} disabled={currentPage === 0}>
          Previous
        </button>
        <button onClick={handleNextPage} disabled={currentPage === totalPages - 1}>
          Next
        </button>
      </div>
    </div>
  );
};

export default FreeSearch;