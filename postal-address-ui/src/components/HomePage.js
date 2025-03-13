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
    <div className="container min-vh-100 d-flex justify-content-center align-items-center bg-light py-5">
      <div className="card p-4 shadow-sm w-100" style={{ maxWidth: '500px' }}>
        <h1 className="text-center mb-4">Search Type Selection</h1>

        <div className="form-check">
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

        {searchType === 'guided' && <GuidedSearch />}
        {searchType === 'free' && <FreeSearch />}
      </div>
    </div>
  );
};

export default HomePage;
