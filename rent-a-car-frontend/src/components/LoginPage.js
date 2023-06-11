import React, {useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const handleUsernameChange = (e) => {
        setUsername(e.target.value);
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/users/login?username=' + username + '&password=' + password,);
            console.log(response)
            if (response.data.success === true) {
                navigate('/home');
                setIsLoggedIn(true);
            }
        } catch (error) {
            setError(error.response.data.data)
            setIsLoggedIn(false);
        }
    };

    return (
        <section style={registerSectionStyle}>
            <h2 style={registerHeadingStyle}>Sign In</h2>
            <div style={divSeparator}>
                <div style={labelDiv}>
                    <label>
                        Username
                    </label>
                </div>
                <div style={inputDiv}>
                    <input
                        style={{...registerInputStyle, ...inputStyle}}
                        type='text'
                        id='username'
                        value={username}
                        onChange={handleUsernameChange}
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
            <form style={registerFormStyle} onSubmit={handleLogin}>
                <button type='submit' style={registerButtonStyle} onClick={handleLogin}>
                    Sign In
                </button>
            </form>
            <div>
                <p>{error}</p>
            </div>
        </section>
    );
}

export default LoginPage;

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
    fontWeight: 'bolder'
};
const inputDiv = {
    width: '150%'
};

const inputStyle = {
    flex: '1',
};