import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles.css'; // Import the CSS file

const FreeSearch = () => {
  const [address, setAddress] = useState({
    address01: '',
    address02: '',
    postalCode: '',
  });

  const [countries, setCountries] = useState([]);
  const [states, setStates] = useState([]);
  const [cities, setCities] = useState([]);
  const [countryId, setCountryId] = useState('');
  const [stateId, setStateId] = useState('');
  const [cityId, setCityId] = useState('');
  const [addresses, setAddresses] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [showTable, setShowTable] = useState(false); // State to control table visibility

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

  const handleCountryChange = (event) => {
    const selectedCountryId = event.target.value;
    setCountryId(selectedCountryId);
    setStateId('');
    setCityId('');
    setStates([]);
    setCities([]);
    setAddresses([]);

    if (selectedCountryId) {
      // Fetch states based on selected country ID
      axios.get(`http://localhost:9091/api/v1/location/countries/${selectedCountryId}/states`)
        .then((response) => {
          setStates(response.data);
        })
        .catch((error) => {
          console.error('Error fetching states:', error);
        });
    }
  };

  const handleStateChange = (event) => {
    const selectedStateId = event.target.value;
    setStateId(selectedStateId);
    setCityId('');
    setCities([]);
    setAddresses([]);

    if (selectedStateId) {
      // Fetch cities based on selected state ID
      axios.get(`http://localhost:9091/api/v1/location/states/${selectedStateId}/cities`)
        .then((response) => {
          setCities(response.data);
        })
        .catch((error) => {
          console.error('Error fetching cities:', error);
        });
    }
  };

  const handleCityChange = (event) => {
    const selectedCityId = event.target.value;
    setCityId(selectedCityId);
    setAddresses([]);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setAddress((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Construct the request body with all fields, even if they are blank
    const requestBody = {
      address01: address.address01 || '',
      countryId: countryId || '',
      stateId: stateId || '',
      cityId: cityId || '',
    };

    console.log('Request Body:', requestBody); // Log the request body

    // Call the new API with the constructed request body
    try {
      const response = await axios.post(`http://localhost:9091/api/v1/addresses/search?page=${currentPage}`, requestBody, {
        params: {
          size: 10, // Limit to 10 addresses per page
        },
      });
      console.log('API Response:', response.data); // Log the API response
      setAddresses(response.data.content);
      setCurrentPage(response.data.pageable.pageNumber);
      setTotalPages(response.data.totalPages);
      setShowTable(true); // Show the table after successful validation
    } catch (error) {
      if (error.response && error.response.status === 404) {
        alert('No address found for your search');
      } else {
        console.error('Error validating address:', error);
        alert('At least one search field must be provided');
      }
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      fetchPage(currentPage + 1);
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      fetchPage(currentPage - 1);
    }
  };

  const fetchPage = async (page) => {
    try {
      const requestBody = {
        address01: address.address01 || '',
        countryId: countryId || '',
        stateId: stateId || '',
        cityId: cityId || '',
      };
      const response = await axios.post(`http://localhost:9091/api/v1/addresses/search?page=${page}`, requestBody, {
        params: {
          size: 10, // Limit to 10 addresses per page
        },
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
        <h4 className="mb-3 text-primary">Free Address Search</h4>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <input
              type="text"
              name="address01"
              value={address.address01}
              onChange={handleChange}
              className="form-control broad-input"
              placeholder="Address Line 1"
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Select Country:</label>
            <select className="form-select broad-input" value={countryId} onChange={handleCountryChange}>
              <option value="">Select a country</option>
              {countries.map((c) => (
                <option key={c.id} value={c.id}>{c.name}</option>
              ))}
            </select>
          </div>
          {countryId && (
            <div className="mb-3">
              <label className="form-label">Select State:</label>
              <select className="form-select broad-input" value={stateId} onChange={handleStateChange}>
                <option value="">Select a state</option>
                {states.map((s) => (
                  <option key={s.id} value={s.id}>{s.name}</option>
                ))}
              </select>
            </div>
          )}
          {stateId && (
            <div className="mb-3">
              <label className="form-label">Select City:</label>
              <select className="form-select broad-input" value={cityId} onChange={handleCityChange}>
                <option value="">Select a city</option>
                {cities.map((c) => (
                  <option key={c.id} value={c.id}>{c.name}</option>
                ))}
              </select>
            </div>
          )}
          <button type="submit" className="btn btn-primary w-100">Validate</button>
        </form>

        {showTable && (
          <div className="mt-4">
            <h2>Addresses</h2>
            <table className="table table-bordered">
              <thead>
                <tr>
                  <th>Address Line 1</th>
                  <th>Address Line 2</th>
                  <th>Postal Code</th>
                  <th>City</th>
                  <th>State</th>
                  <th>Country</th>
                </tr>
              </thead>
              <tbody>
                {addresses.map((addr) => (
                  <tr key={addr.id}>
                    <td>{addr.address01}</td>
                    <td>{addr.address02}</td>
                    <td>{addr.postalCode}</td>
                    <td>{addr.cityName}</td>
                    <td>{addr.stateName}</td>
                    <td>{addr.CounrtyName}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            <div style={{ marginTop: '10px' }}>
              <button onClick={handlePreviousPage} disabled={currentPage === 0}>
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

export default FreeSearch;