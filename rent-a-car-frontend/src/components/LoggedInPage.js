import {useState} from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";

function LoggedIn() {
    const [rentDate, setRentDate] = useState('');
    const [returnDate, setReturnDate] = useState('');
    const [rentCityId, setRentCityId] = useState('');
    const [returnCityId, setReturnCityId] = useState('');
    const [returnKilometer, setReturnKilometer] = useState(0);
    const [carId, setCarId] = useState('');
    const [customerId, setCustomerId] = useState('');
    const [additionalServiceIds, setAdditionalServiceIds] = useState([]);

    const [cardNo, setCardNo] = useState('');
    const [cardHolder, setCardHolder] = useState('');
    const [expirationMonth, setExpirationMonth] = useState(0);
    const [expirationYear, setExpirationYear] = useState(0);
    const [cvv, setCvv] = useState(0);

    const [isCorporate, setIsCorporate] = useState(false);
    const [isIndividual, setIsIndividual] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleCorporateCustomerSection = async (e) => {
        setIsIndividual(false);
        setIsCorporate(true);
        e.preventDefault();
        const createRentalCarRequest = {
            rentDate,
            returnDate,
            rentCityId,
            returnCityId,
            returnKilometer,
            carId,
            customerId,
            additionalServiceIds
        };

        const createPaymentRequest = {
            cardNo,
            cardHolder,
            expirationMonth,
            expirationYear,
            cvv
        };

        const createPaymentModel = {
            createRentalCarRequest,
            createPaymentRequest
        };

        try {
            if (isCorporate === true) {
                const response =
                    await axios.post("http://localhost:8080/api/paymentsController/paymentForCorporateCustomer", createPaymentModel);
                console.log(response)
                if (response.data.success === true) {
                    navigate('/home');
                }
            }
            if (isIndividual === true) {
                const response =
                    await axios.post("http://localhost:8080/api/paymentsController/paymentForIndividualCustomer", createPaymentModel);
                console.log(response)
                if (response.data.success === true) {
                    navigate('/home');
                }
            }
        } catch (error) {
            if (error.response.data.message === "Validation Error: ") {
                setError(Object.values(error.response.data.data))
            } else {
                setError(error.response.data.data)
            }
        }
    };
    const handleIndividualCustomerSection = async (e) => {
        setIsCorporate(false);
        setIsIndividual(true);
        e.preventDefault();
        const createRentalCarRequest = {
            rentDate,
            returnDate,
            rentCityId,
            returnCityId,
            returnKilometer,
            carId,
            customerId,
            additionalServiceIds
        };

        const createPaymentRequest = {
            cardNo,
            cardHolder,
            expirationMonth,
            expirationYear,
            cvv
        };

        const createPaymentModel = {
            createRentalCarRequest,
            createPaymentRequest
        };

        try {
            if (isCorporate === true) {
                const response =
                    await axios.post("http://localhost:8080/api/paymentsController/paymentForCorporateCustomer", createPaymentModel);
                console.log(response)
                if (response.data.success === true) {
                    navigate('/home');
                }
            }
            if (isIndividual === true) {
                const response =
                    await axios.post("http://localhost:8080/api/paymentsController/paymentForIndividualCustomer", createPaymentModel);
                console.log(response)
                if (response.data.success === true) {
                    navigate('/home');
                }
            }
        } catch (error) {
            if (error.response.data.message === "Validation Error: ") {
                setError(Object.values(error.response.data.data))
            } else {
                setError(error.response.data.data)
            }
        }
    };

    const handleRentalCarSubmit = async (e) => {

    };

    return (
        <section style={registerSectionStyle}>
            <h2 style={registerHeadingStyle}>Please select customer type for rent</h2>
            <div >
                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Rent Date
                        </label>
                    </div>
                    <div style={inputDiv}>
                    <input type="date" style={registerInputStyle} value={rentDate}
                           onChange={e => setRentDate(e.target.value)}/>
                    </div>
                </div>

                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Return Date
                        </label>
                    </div>
                    <div style={inputDiv}>
                    <input type="date" style={registerInputStyle} value={returnDate}
                           onChange={e => setReturnDate(e.target.value)}/>
                    </div>
                </div>

                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Rent Cit Id
                        </label>
                    </div>
                    <div style={inputDiv}>
                    <input type="text" style={registerInputStyle} value={rentCityId}
                           onChange={e => setRentCityId(e.target.value)}/>
                    </div>
                </div>

                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Return Cit Id
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input type="text" style={registerInputStyle} value={returnCityId}
                               onChange={e => setReturnCityId(e.target.value)}/>
                    </div>
                </div>

                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Return Kilometer
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input type="number" style={registerInputStyle} value={returnKilometer}
                               onChange={e => setReturnKilometer(parseFloat(e.target.value))}/>
                    </div>
                </div>

                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Car Id
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input type="text" style={registerInputStyle} value={carId}
                               onChange={e => setCarId(e.target.value)}/>
                    </div>
                </div>

                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Customer Id
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input type="text" style={registerInputStyle} value={customerId}
                               onChange={e => setCustomerId(e.target.value)}/>
                    </div>
                </div>

                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Additional Product Ids
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input type="text" style={registerInputStyle} value={additionalServiceIds}
                               onChange={e => setAdditionalServiceIds(e.target.value.split(','))}/>
                    </div>
                </div>

                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Card No
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input type="text" style={registerInputStyle} value={cardNo}
                               onChange={e => setCardNo(e.target.value)}/>

                    </div>
                </div>

                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Card Holder
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input type="text" style={registerInputStyle} value={cardHolder}
                               onChange={e => setCardHolder(e.target.value)}/>
                    </div>
                </div>
                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Expiration Month
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input type="number" style={registerInputStyle} value={expirationMonth}
                               onChange={e => setExpirationMonth(parseInt(e.target.value))}/>
                    </div>
                </div>

                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Expiration Year
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input type="number" style={registerInputStyle} value={expirationYear}
                               onChange={e => setExpirationYear(parseInt(e.target.value))}/>
                    </div>
                </div>
                <div style={divSeparator}>
                    <div style={labelDiv}>
                        <label>
                            Cvv
                        </label>
                    </div>
                    <div style={inputDiv}>
                        <input type="number" style={registerInputStyle} value={cvv}
                               onChange={e => setCvv(parseInt(e.target.value))}/>
                    </div>
                </div>
            </div>

            <div style={selectButtonsDiv}>
                <form style={registerFormStyle}>
                    <button type='submit' style={selectButtonsStyleCorporate}
                            onClick={handleCorporateCustomerSection}>
                        Corporate Customer Rent
                    </button>
                </form>
                <form style={registerFormStyle}>
                    <button type='submit' style={selectButtonsStyleIndividual}
                            onClick={handleIndividualCustomerSection} >
                        Individual Customer Rent
                    </button>
                </form>
            </div>
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
}

export default LoggedIn;
const errorFont = {
    color: 'red',
    fontSize: 'xx-large'
}
const registerInputStyle = {
    marginBottom: '10px',
    padding: '10px',
    border: '1px solid #ccc',
    borderRadius: '5px'
};

const selectButtonsDiv = {
    display: 'flex',
    flexDirection: 'row',


}

const selectButtonsStyleCorporate = {
    padding: '20px',
    backgroundColor: '#f9a82d',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontSize: '24px',
    marginRight: '1%'
}
const selectButtonsStyleIndividual = {
    padding: '20px',
    backgroundColor: '#f9a82d',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontSize: '24px',
    marginLeft: '1%'
}
const registerHeadingStyle = {
    textAlign: 'center',
    marginBottom: '20px'
};

const registerFormStyle = {
    display: 'flex',
    flexDirection: 'column'
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
    flexDirection: "row"
};
const mainDiv = {
    display: "flex",
    flexDirection: "column",
    justifyContent: 'space-between',
};
const labelDiv = {
    width: '50%',
    fontWeight: 'bolder'
};
const inputDiv = {
    width: '100%'
};

const inputStyle = {
    flex: '1',
};