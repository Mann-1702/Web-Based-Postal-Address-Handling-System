import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AddressForm from './components/AddressForm';
import AddressList from './components/AddressList';
import './styles.css';
import HomePage from './components/HomePage';
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {
  return (
    <Router>
      <div>
        {/* <h1>Postal Address Handling System1</h1> */}
        <Routes>
          <Route path="/" element={<HomePage />} />
          {/* <Route path="/add" element={<AddressForm />} /> */}
        </Routes>
      </div>
    </Router>
  );
}

export default App;
