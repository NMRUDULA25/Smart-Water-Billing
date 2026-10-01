import { Link } from "react-router-dom";

function Sidebar() {

    return (
        <div
            className="bg-dark text-white p-3"
            style={{
                width: "185px",
                minHeight: "calc(100vh - 56px)"
            }}
        >

            <h4 className="text-center mb-4">
                Menu
            </h4>

            <ul className="nav flex-column">

                <li className="nav-item mb-2">
                    <Link
                        to="/dashboard"
                        className="nav-link text-white"
                    >
                        <i className="bi bi-speedometer2 me-2"></i>
                        Dashboard
                    </Link>
                </li>

                <li className="nav-item mb-2">
                    <Link
                        to="/consumers"
                        className="nav-link text-white"
                    >
                        <i className="bi bi-people-fill me-2"></i>
                        Consumers
                    </Link>
                </li>

                <li className="nav-item mb-2">
                    <Link
                        to="/meter-readings"
                        className="nav-link text-white"
                    >
                        <i className="bi bi-droplet-fill me-2"></i>
                        Meter Readings
                    </Link>
                </li>

                <li className="nav-item mb-2">
                    <Link
                        to="/bills"
                        className="nav-link text-white"
                    >
                        <i className="bi bi-receipt me-2"></i>
                        Bills
                    </Link>
                </li>

                <li className="nav-item mb-2">
                    <Link
                        to="/invoices"
                        className="nav-link text-white"
                    >
                        <i className="bi bi-file-earmark-pdf me-2"></i>
                        Invoices
                    </Link>
                </li>

                <li className="nav-item mb-2">
                    <Link
                        to="/reports"
                        className="nav-link text-white"
                    >
                        <i className="bi bi-bar-chart-fill me-2"></i>
                        Reports
                    </Link>
                </li>

                <li className="nav-item mb-2">
                    <Link
                        to="/billing-cycles"
                        className="nav-link text-white"
                    >
                        <i className="bi bi-calendar3 me-2"></i>
                        Billing Cycles
                    </Link>
                </li>

                <li className="nav-item mb-2">
                    <Link
                        to="/payments"
                        className="nav-link text-white"
                    >
                        <i className="bi bi-credit-card me-2"></i>
                        Payments
                    </Link>
                </li>

                <li className="nav-item mb-2">
                    <Link
                        to="/tariff-plans"
                        className="nav-link text-white"
                    >
                        <i className="bi bi-tags me-2"></i>
                        Tariff Plans
                    </Link>
                </li>

                <li className="nav-item mb-2">
                    <Link
                        to="/bulk-water-purchases"
                        className="nav-link text-white"
                    >
                        <i className="bi bi-truck me-2"></i>
                        Bulk Water
                    </Link>
                </li>
                <li className="nav-item">

                    <Link
                        to="/consumption-comparison"
                        className="nav-link text-white"
                    >

                        <i className="bi bi-bar-chart-line me-2"></i>

                        Consumption Comparison

                    </Link>

                </li>
                <li className="nav-item">
                    <Link
                        to="/reports"
                        className="nav-link text-white"
                    >
                        <i className="bi bi-bar-chart-fill me-2"></i>
                        Reports
                    </Link>
                </li>

            </ul>

        </div>
    );
}

export default Sidebar;