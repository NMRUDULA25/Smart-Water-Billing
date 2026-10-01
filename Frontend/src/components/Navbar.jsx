import { Link } from "react-router-dom";

function Navbar() {

    const username = localStorage.getItem("username");

    const logout = () => {
        localStorage.clear();
        window.location.href = "/";
    };

    return (

        <nav className="navbar navbar-expand-lg navbar-dark bg-primary">

            <div className="container-fluid">

                <Link className="navbar-brand fw-bold" to="/dashboard">
                    Smart Water Billing
                </Link>

                <div className="ms-auto d-flex align-items-center">

                    <span className="text-white me-3">
                        Welcome, <b>{username}</b>
                    </span>

                    <button
                        className="btn btn-light btn-sm"
                        onClick={logout}
                    >
                        Logout
                    </button>

                </div>

            </div>

        </nav>

    );

}

export default Navbar;