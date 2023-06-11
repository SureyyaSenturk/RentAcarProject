import {useState} from 'react';
import axios from "axios";
import logo from "../images/corporate.png";
import {useNavigate} from "react-router-dom";

const url = 'http://localhost:8080/api/corporateCustomersController/register';

function CorporateCustomerRegisterPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [companyName, setCompanyName] = useState('');
    const [taxNumber, setTaxNumber] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const [showError, setShowError] = useState(false);

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleCompanyNameChange = (event) => {
        setCompanyName(event.target.value);
    };

    const handleTaxNumberChange = (event) => {
        setTaxNumber(event.target.value);
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        const data = {
            email: email,
            password: password,
            companyName: companyName,
            taxNumber: taxNumber
        };
        try {
            const response = await axios.post(url, data);
            console.log(response)
            if (response.status === 200) {
                navigate('/login');
                setShowError(false);
            }
        } catch (error) {
            if (error.response.data.message === "Validation Error: ") {
                setError(Object.values(error.response.data.data))
            } else {
                setError(error.response.data.data)
            }
        }
    };

    return (
        <section style={registerSectionStyle}>
            <h2 style={registerHeadingStyle}>Corporate Customer Registration</h2>
            <img src={logo} width="100%" height="100%"/>
            <div style={mainDiv}>
                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Email
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input
                            style={{...registerInputStyle, ...inputStyle}}
                            type='email'

                            id='email'
                            value={email}
                            onChange={handleEmailChange}
                        />
                    </div>
                </div>

                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Password
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input
                            style={registerInputStyle}
                            type='password'
                            className='form-input'
                            id='password'
                            value={password}
                            onChange={handlePasswordChange}
                        />
                    </div>
                </div>
                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Company Name
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input
                            style={registerInputStyle}
                            type='text'
                            className='form-input'
                            id='companyName'
                            value={companyName}
                            onChange={handleCompanyNameChange}
                        />
                    </div>
                </div>
                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Tax Number
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input
                            style={registerInputStyle}
                            type='text'
                            className='form-input'
                            id='taxNumber'
                            value={taxNumber}
                            onChange={handleTaxNumberChange}
                        />
                    </div>
                </div>
            </div>
            <form style={registerFormStyle} onSubmit={handleSubmit}>
                <button type='submit' style={registerButtonStyle} onClick={handleSubmit}>
                    Register
                </button>
            </form>
            {Array.isArray(error) ? (
                <div>
                    <ul style={errorFont}>
                        {error.map((errorMessage, index) => (
                            <li key={index}>{errorMessage}</li>
                        ))}
                    </ul>
                </div>
            ) : (
                <div>
                    <p style={errorFont}>{error}</p>
                </div>
            )}
        </section>
    );
};

export default CorporateCustomerRegisterPage;
const errorFont = {
    color: 'red',
    fontSize: 'xx-large'
}
const registerHeadingStyle = {
    textAlign: 'center',
    marginBottom: '20px'
};

const registerFormStyle = {
    display: 'flex',
    flexDirection: 'column'
};

const registerInputStyle = {
    marginBottom: '10px',
    padding: '10px',
    border: '1px solid #ccc',
    borderRadius: '5px'
};

const registerButtonStyle = {
    padding: '10px',
    backgroundColor: '#f9a82d',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer'
};

const registerSectionStyle = {
    width: '100%',
    margin: '0 auto',
    padding: '20px',
    backgroundColor: '#f2f2f2',
    borderRadius: '5px',
    backgroundSize: 'cover',
    backgroundPosition: 'center'
};


const divSeparator = {
    display: "flex",
    flexDirection: "row",
    justifyContent: 'space-between',
};
const mainDiv = {
    display: "flex",
    flexDirection: "column",
    justifyContent: 'space-between',
};
const labelDiv = {
    width: '20%',
    fontWeight:'bolder'
};
const inputDiv = {
    width: '150%'
};

const inputStyle = {
    flex: '1',
};
