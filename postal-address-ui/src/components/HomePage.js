import React, { useState } from 'react';
import '../styles.css';  
import GuidedSearch from './GuidedSearch';
import FreeSearch from './FreeSearch';

const HomePage = () => {
  const [searchType, setSearchType] = useState('');

  const handleSearchTypeChange = (event) => {
    setSearchType(event.target.value);
  };

  return (
    <div className="container min-vh-50 d-flex justify-content-center align-items-center bg-light py-5">
      <div className="card p-5 shadow-lg w-100" style={{ maxWidth: '1200px' }}>
        <h1 className="text-center mb-4">Postal Address Handling System</h1>
        <p className="text-center mb-4">Please select a search type to proceed:</p>

        <div className="form-check mb-3">
          <input
            type="radio"
            className="form-check-input"
            value="guided"
            checked={searchType === 'guided'}
            onChange={handleSearchTypeChange}
            id="guidedSearch"
          />
          <label className="form-check-label" htmlFor="guidedSearch">
            Guided Search
          </label>
        </div>

        <div className="form-check mb-4">
          <input
            type="radio"
            className="form-check-input"
            value="free"
            checked={searchType === 'free'}
            onChange={handleSearchTypeChange}
            id="freeSearch"
          />
          <label className="form-check-label" htmlFor="freeSearch">
            Free Search
          </label>
        </div>

        {searchType === 'guided' && (
          <div className="mt-4">
            <GuidedSearch />
          </div>
        )}
        {searchType === 'free' && (
          <div className="mt-4">
            <FreeSearch />
          </div>
        )}
      </div>
    </div>
  );
};

export default HomePage;