import React, { useState } from 'react';
// import './styles.css';
import GuidedSearch from './GuidedSearch';
import FreeSearch from './FreeSearch';

const HomePage = () => {
  const [searchType, setSearchType] = useState('');

  const handleSearchTypeChange = (event) => {
    setSearchType(event.target.value);
  };

  return (
    <div>
      <h1>Postal Address Handling System</h1>

      <div>
        <label>
          <input
            type="radio"
            value="guided"
            checked={searchType === 'guided'}
            onChange={handleSearchTypeChange}
          />
          Guided Search
        </label>
        <label>
          <input
            type="radio"
            value="free"
            checked={searchType === 'free'}
            onChange={handleSearchTypeChange}
          />
          Free Search
        </label>
      </div>

      {searchType === 'guided' && <GuidedSearch />}
      {searchType === 'free' && <FreeSearch />}
    </div>
  );
};

export default HomePage;
