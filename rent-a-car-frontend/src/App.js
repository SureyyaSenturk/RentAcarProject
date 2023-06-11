import React, {useEffect, useState} from 'react';
import {BrowserRouter as Router, Route, Routes,useNavigate } from 'react-router-dom';
import LoginPage from "./components/LoginPage";
import CorporateCustomerRegisterPage from "./components/CorporateCustomerRegisterPage";
import logo from './images/homepage-header.png';
import axios from 'axios';
import IndividualCustomerRegisterPage from "./components/IndividualCustomerRegisterPage";
import LoggedInPage from "./components/LoggedInPage";

const Home = () => {
    const navigate = useNavigate();

    const loginButtonClickHandle = () => {
        navigate('/login');
    };
    const corporateCustomerRentClickHandle = () => {
        navigate('/corporateCustomerRent');
    };
    const individualCustomerRentClickHandle = () => {
        navigate('/individualCustomerRent');
    };

    const [cityList, setCityList] = useState([]);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/cities/getAll');
            const data = response.data.data;

            setCityList(data); //
        } catch (error) {
            console.error(error);
        }
    };
    return (
        <div>
            <div style={headerStyle}>
                <h2 style={headingStyle}>Welcome to Senturk Rent A Car</h2>
                <div style={{display: "flex", flexDirection: "row", height: "100%", marginLeft: "25%"}}>
                    <button style={navBarButton}onClick={individualCustomerRentClickHandle}>Individual Customer Sign Up</button>
                    <button style={navBarButton} onClick={corporateCustomerRentClickHandle}>Corporate Customer Sign Up</button>
                </div>
                <div style={{display: "flex", flexDirection: "row", height: "100%"}}>
                    <button style={signInStyle} onClick={loginButtonClickHandle}>Sign In</button>
                </div>
            </div>
            <img src={logo} width="100%" height="100%"/>
            <h2 style={headingStyle}>Hizmet Verdiğimiz
                Şehirler</h2>
            <table style={{marginLeft: "40%"}}>
                <thead>
                <tr style={{fontSize:'xxx-large'}}>
                    <th>City</th>
                    <th>Plate</th>
                </tr>
                </thead>
                <tbody style={{fontSize:'xxx-large'}}>
                {cityList.map((city) => (
                    <tr key={city.id}>
                        <th>{city.name}</th>
                        <th>{city.code}</th>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};
const App = () => {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/login" element={<LoginPage/>}/>
                    <Route path="/corporateCustomerRegister" element={<CorporateCustomerRegisterPage/>}/>
                    <Route path="/individualCustomerRegister" element={<IndividualCustomerRegisterPage/>}/>
                    <Route path="/home" element={<LoggedInPage/>}/>
                </Routes>
            </div>
        </Router>
    );
};
export default App;

const signInStyle = {
    fontSize: 'larger',
    height: '50%',
    width: '120px',
    padding: '10px 20px',
    backgroundColor: '#007bff',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
};

const navBarButton = {
    fontSize: '24px',
    padding: '10px 20px',
    backgroundColor: '#28a745',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
};

const headingStyle = {
    fontSize: '36px',
    fontWeight: 'bold',
    textAlign: 'center',
    color: 'red',
    textShadow: '2px 2px 4px rgba(0, 0, 0, 0.2)',
    margin: '20px 0',
};

const headerStyle = {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
};
const selectButton = {
    padding: "8px",
    border: "1px solid #ccc",
    borderRadius: "4px",
    backgroundColor: "#fff",
    color: "#333",
    fontSize: "14px",
    cursor: "pointer",
};
