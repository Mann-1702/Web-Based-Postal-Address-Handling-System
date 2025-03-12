import React, { useState, useEffect } from "react";
import axios from "axios";

const GuidedSearch = () => {
  const [country, setCountry] = useState("");
  const [state, setState] = useState("");
  const [city, setCity] = useState("");
  const [countries, setCountries] = useState([]);
  const [states, setStates] = useState([]);
  const [cities, setCities] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:9091/api/v1/location/countries")
      .then((response) => {
        // Remove duplicate country names
        const uniqueCountries = Array.from(
          new Map(response.data.map((c) => [c.name, c])).values()
        );
        setCountries(uniqueCountries);
      })
      .catch((error) => {
        console.error("Error fetching countries:", error);
      });
  }, []);

  const handleCountryChange = (event) => {
    const selectedCountry = event.target.value;
    setCountry(selectedCountry);
    setState("");
    setCity("");
    setStates([]);
    setCities([]);

    axios
      .get(`http://localhost:9091/api/v1/location/states?country=${selectedCountry}`)
      .then((response) => {
        setStates(response.data);
      })
      .catch((error) => {
        console.error("Error fetching states:", error);
      });
  };

  const handleStateChange = (event) => {
    const selectedState = event.target.value;
    setState(selectedState);
    setCity("");
    setCities([]);

    axios
      .get(`http://localhost:9091/api/v1/location/cities?state=${selectedState}`)
      .then((response) => {
        setCities(response.data);
      })
      .catch((error) => {
        console.error("Error fetching cities:", error);
      });
  };

  return (
    <div>
      <label>
        Select Country:
        <select value={country} onChange={handleCountryChange}>
          <option value="">Select a country</option>
          {countries.map((c) => (
            <option key={c.id} value={c.name}>
              {c.name}
            </option>
          ))}
        </select>
      </label>

      {country && (
        <label>
          Select State:
          <select value={state} onChange={handleStateChange}>
            <option value="">Select a state</option>
            {states.map((s) => (
              <option key={s.id} value={s.name}>
                {s.name}
              </option>
            ))}
          </select>
        </label>
      )}

      {state && (
        <label>
          Select City:
          <select value={city} onChange={(e) => setCity(e.target.value)}>
            <option value="">Select a city</option>
            {cities.map((c) => (
              <option key={c.id} value={c.name}>
                {c.name}
              </option>
            ))}
          </select>
        </label>
      )}
    </div>
  );
};

export default GuidedSearch;
