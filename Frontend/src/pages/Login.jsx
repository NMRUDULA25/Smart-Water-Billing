import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { login } from "../services/authService";

function Login() {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        username: "",
        password: ""
    });

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const response = await login(formData);

            localStorage.setItem("token", response.token);
            localStorage.setItem("username", response.username);
            localStorage.setItem("role", response.role);

            alert("Login Successful");

            navigate("/dashboard");

        } catch (error) {

            alert("Invalid Username or Password");
            console.log(error);

        }
    };

    return (

        <div className="container mt-5">

            <div className="row justify-content-center">

                <div className="col-md-5">

                    <div className="card shadow">

                        <div className="card-header bg-primary text-white text-center">

                            <h3>Smart Water Billing</h3>

                        </div>

                        <div className="card-body">

                            <form onSubmit={handleSubmit}>

                                <div className="mb-3">

                                    <label>Username</label>

                                    <input
                                        type="text"
                                        name="username"
                                        className="form-control"
                                        value={formData.username}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                                <div className="mb-3">

                                    <label>Password</label>

                                    <input
                                        type="password"
                                        name="password"
                                        className="form-control"
                                        value={formData.password}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                                <button className="btn btn-primary w-100">

                                    Login

                                </button>

                            </form>

                            <hr />

                            <p className="text-center">

                                New User?

                                <Link to="/register">

                                    Register Here

                                </Link>

                            </p>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );
}

export default Login;