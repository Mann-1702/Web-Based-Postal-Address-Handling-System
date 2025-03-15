import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

const GuidedSearch = () => {
  const [country, setCountry] = useState('');
  const [countryId, setCountryId] = useState('');
  const [state, setState] = useState('');
  const [stateId, setStateId] = useState('');
  const [city, setCity] = useState('');
  const [cityId, setCityId] = useState('');
  const [address01, setAddressLine1] = useState(''); // address01
  const [countries, setCountries] = useState([]);
  const [states, setStates] = useState([]);
  const [cities, setCities] = useState([]);
  const [addresses, setAddresses] = useState([]); // Stores fetched addresses
  const [loadingStates, setLoadingStates] = useState(false);
  const [loadingCities, setLoadingCities] = useState(false);
  const [loadingAddresses, setLoadingAddresses] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0); // Track total pages
  const [showTable, setShowTable] = useState(false); // State to control table visibility

  // Fetch countries
  useEffect(() => {
    axios.get('http://localhost:9091/api/v1/location/countries')
      .then((response) => {
        setCountries(response.data);
      })
      .catch((error) => {
        console.error('Error fetching countries:', error);
      });
  }, []);

  const handleCountryChange = (event) => {
    const selectedCountry = event.target.value;
    const selectedCountryData = countries.find((c) => c.name === selectedCountry);
    setCountry(selectedCountry);
    setCountryId(selectedCountryData ? selectedCountryData.id : '');
    setState('');
    setStateId('');
    setCity('');
    setCityId('');
    setStates([]);
    setCities([]);
    setAddresses([]);
    setLoadingStates(true);

    // Fetch states based on selected country ID
    if (selectedCountryData) {
      axios.get(`http://localhost:9091/api/v1/location/countries/${selectedCountryData.id}/states`)
        .then((response) => {
          setStates(response.data);
          setLoadingStates(false);
        })
        .catch((error) => {
          console.error('Error fetching states:', error);
          setLoadingStates(false);
        });
    }
  };

  const handleStateChange = (event) => {
    const selectedState = event.target.value;
    const selectedStateData = states.find((s) => s.name === selectedState);
    setState(selectedState);
    setStateId(selectedStateData ? selectedStateData.id : '');
    setCity('');
    setCityId('');
    setCities([]);
    setAddresses([]);
    setLoadingCities(true);

    // Fetch cities based on selected state ID
    if (selectedStateData) {
      axios.get(`http://localhost:9091/api/v1/location/states/${selectedStateData.id}/cities`)
        .then((response) => {
          setCities(response.data);
          setLoadingCities(false);
        })
        .catch((error) => {
          console.error('Error fetching cities:', error);
          setLoadingCities(false);
        });
    }
  };

  const handleCityChange = (event) => {
    const selectedCity = event.target.value;
    const selectedCityData = cities.find((c) => c.name === selectedCity);
    setCity(selectedCity);
    setCityId(selectedCityData ? selectedCityData.id : '');
    setAddresses([]); // Clear previous suggestions when a new city is selected
    setLoadingAddresses(true);

    // Fetch addresses based on selected cityId (and other query parameters if necessary)
    if (selectedCityData) {
      axios.get(`http://localhost:9091/api/v1/addresses/city/${selectedCityData.id}`)
        .then((response) => {
          setAddresses(response.data.slice(0, 5)); // Limit to 5 addresses
          setLoadingAddresses(false);
        })
        .catch((error) => {
          console.error('Error fetching addresses:', error);
          setLoadingAddresses(false);
        });
    }
  };

  // Handle address search input
  const handleAddressSearch = (event) => {
    const searchQuery = event.target.value;
    setAddressLine1(searchQuery); // Update the state with the search query

    // Fetch addresses dynamically based on the typed value in addressLine1
    if (searchQuery.trim()) {
      setLoadingAddresses(true);
      axios.get(`http://localhost:9091/api/v1/addresses/search?address01=${searchQuery}&cityId=${cityId}`)
        .then((response) => {
          setAddresses(response.data.slice(0, 5)); // Limit to 5 addresses
          setLoadingAddresses(false);
        })
        .catch((error) => {
          console.error('Error fetching addresses:', error);
          setLoadingAddresses(false);
        });
    } else {
      setAddresses([]); // Clear suggestions if the search query is empty
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      fetchPage(currentPage + 1);
    }
  };

  const handlePrevPage = () => {
    if (currentPage > 0) {
      fetchPage(currentPage - 1);
    }
  };

  // Handle submit
  const handleSubmit = async (e) => {
    e.preventDefault();
    const searchParams = {
      address01,
      cityId,
      stateId,
      countryId,
    };

    const paginationParams = {
      page: currentPage,
      size: pageSize,
    };

    const hasValue = Object.values(searchParams).some((val) => val);
    if (!hasValue) {
      alert("Please enter at least one search criteria.");
      return;
    }

    try {
      const response = await axios.post(`http://localhost:9091/api/v1/addresses/search?page=${currentPage}`, {
        ...searchParams,
        ...paginationParams,
      });

      setAddresses(response.data.content); // Assuming "content" contains the paginated results
      setTotalPages(response.data.totalPages); // Set total pages
      setShowTable(true); // Show the table after successful validation
    } catch (error) {
      console.error("Error fetching search results:", error);
    }
  };

  const fetchPage = async (page) => {
    const searchParams = {
      address01,
      cityId,
      stateId,
      countryId,
    };

    const paginationParams = {
      page: page,
      size: pageSize,
    };

    try {
      const response = await axios.post(`http://localhost:9091/api/v1/addresses/search?page=${page}`, {
        ...searchParams,
        ...paginationParams,
      });
      setAddresses(response.data.content);
      setCurrentPage(response.data.pageable.pageNumber);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error('Error fetching page:', error);
    }
  };

  return (
    <div className="container mt-4">
      <div className="card p-4 shadow-sm">
        <h4 className="mb-3 text-primary">Guided Address Search</h4>

        {/* Country Selection */}
        <div className="mb-3">
          <label className="form-label">Select Country:</label>
          <select className="form-select" value={country} onChange={handleCountryChange}>
            <option value="">Select a country</option>
            {countries.map((c) => (
              <option key={c.id} value={c.name}>{c.name}</option>
            ))}
          </select>
        </div>

        {/* State Selection */}
        {country && (
          <div className="mb-3">
            <label className="form-label">Select State:</label>
            <select className="form-select" value={state} onChange={handleStateChange} disabled={loadingStates}>
              <option value="">Select a state</option>
              {loadingStates ? <option>Loading...</option> :
                states.map((s) => (
                  <option key={s.id} value={s.name}>{s.name}</option>
                ))
              }
            </select>
          </div>
        )}

        {/* City Selection */}
        {state && (
          <div className="mb-3">
            <label className="form-label">Select City:</label>
            <select className="form-select" value={city} onChange={handleCityChange} disabled={loadingCities}>
              <option value="">Select a city</option>
              {loadingCities ? <option>Loading...</option> :
                cities.map((c) => (
                  <option key={c.id} value={c.name}>{c.name}</option>
                ))
              }
            </select>
          </div>
        )}

        {/* Address Search */}
        {city && (
          <div className="mb-3">
            <label className="form-label">Address Line 1:</label>
            {loadingAddresses && <div>Loading addresses...</div>}
            {addresses.length > 0 && (
              <select className="form-select mt-2" value={address01} onChange={(e) => setAddressLine1(e.target.value)}>
                <option value="">Select an address</option>
                {addresses.map((address) => (
                  <option key={address.id} value={address.address01}>
                    {address.address01}, {address.address02}, {address.postalCode}
                  </option>
                ))}
              </select>
            )}
          </div>
        )}

        {/* Submit Button */}
        <button className="btn btn-primary" onClick={handleSubmit}>Validate</button>

        {/* Display search results */}
        {showTable && (
          <div>
            <h2>Addresses</h2>
            <table style={{ borderCollapse: 'collapse', width: '100%' }}>
              <thead>
                <tr>
                  <th style={{ border: '1px solid black', padding: '8px' }}>Address Line 1</th>
                  <th style={{ border: '1px solid black', padding: '8px' }}>Address Line 2</th>
                  <th style={{ border: '1px solid black', padding: '8px' }}>Postal Code</th>
                  <th style={{ border: '1px solid black', padding: '8px' }}>City</th>
                  <th style={{ border: '1px solid black', padding: '8px' }}>State</th>
                  <th style={{ border: '1px solid black', padding: '8px' }}>Country</th>
                </tr>
              </thead>
              <tbody>
                {addresses.map((addr) => (
                  <tr key={addr.id}>
                    <td style={{ border: '1px solid black', padding: '8px' }}>{addr.address01}</td>
                    <td style={{ border: '1px solid black', padding: '8px' }}>{addr.address02}</td>
                    <td style={{ border: '1px solid black', padding: '8px' }}>{addr.postalCode}</td>
                    <td style={{ border: '1px solid black', padding: '8px' }}>{addr.cityName}</td>
                    <td style={{ border: '1px solid black', padding: '8px' }}>{addr.stateName}</td>
                    <td style={{ border: '1px solid black', padding: '8px' }}>{addr.CounrtyName}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            <div style={{ marginTop: '10px' }}>
              <button onClick={handlePrevPage} disabled={currentPage === 0}>
                Previous
              </button>
              <span style={{ margin: '0 10px' }}>
                Page {currentPage + 1} of {totalPages}
              </span>
              <button onClick={handleNextPage} disabled={currentPage === totalPages - 1}>
                Next
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default GuidedSearch;