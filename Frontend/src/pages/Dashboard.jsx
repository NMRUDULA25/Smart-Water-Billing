import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import DashboardCharts from "../components/DashboardCharts";
import { getDashboardReport } from "../services/reportService";
import { getAllConsumers } from "../services/consumerService";
import { getAllBills } from "../services/billService";
import { getAllPayments } from "../services/paymentService";
function Dashboard() {

    const username = localStorage.getItem("username");

    const [dashboard, setDashboard] = useState({
        totalConsumers: 0,
        totalMeterReadings: 0,
        totalBills: 0,
        paidBills: 0,
        unpaidBills: 0,
        totalPayments: 0,
        totalRevenue: 0
    });
    const [recentConsumers, setRecentConsumers] = useState([]);
    const [recentBills, setRecentBills] = useState([]);
    const [recentPayments, setRecentPayments] = useState([]);

    useEffect(() => {
        loadDashboard();
    }, []);

    const loadDashboard = async () => {

        try {

            const report = await getDashboardReport();
            setDashboard(report.data);

            const consumers = await getAllConsumers();
            setRecentConsumers(consumers.data.slice(-5).reverse());

            const bills = await getAllBills();
            setRecentBills(bills.data.slice(-5).reverse());

            const payments = await getAllPayments();
            setRecentPayments(payments.data.slice(-5).reverse());

        } catch (error) {

            console.log(error);

        }

    };

    return (

        <Layout>

            <h2 className="mb-4">Dashboard</h2>

            <h5 className="mb-4">
                Welcome, {username}
            </h5>

            <div className="row">

                <div className="col-md-3 mb-3">

                    <div className="card bg-primary text-white shadow">

                        <div className="card-body text-center">

                            <i className="bi bi-people-fill display-4"></i>

                            <h5 className="mt-3">Consumers</h5>

                            <h2>{dashboard.totalConsumers}</h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-3 mb-3">

                    <div className="card bg-success text-white shadow">

                        <div className="card-body text-center">

                            <i className="bi bi-droplet-fill display-4"></i>

                            <h5 className="mt-3">Meter Readings</h5>

                            <h2>{dashboard.totalMeterReadings}</h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-3 mb-3">

                    <div className="card bg-warning text-dark shadow">

                        <div className="card-body text-center">

                            <i className="bi bi-receipt-cutoff display-4"></i>

                            <h5 className="mt-3">Bills</h5>

                            <h2>{dashboard.totalBills}</h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-3 mb-3">

                    <div className="card bg-danger text-white shadow">

                        <div className="card-body text-center">

                            <i className="bi bi-currency-rupee display-4"></i>

                            <h5 className="mt-3">Revenue</h5>

                            <h2>₹ {dashboard.totalRevenue}</h2>

                        </div>

                    </div>

                </div>

            </div>

            <div className="row mt-3">

                <div className="col-md-4 mb-3">

                    <div className="card bg-success text-white shadow">

                        <div className="card-body text-center">

                            <h5>Paid Bills</h5>

                            <h2>{dashboard.paidBills}</h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-4 mb-3">

                    <div className="card bg-danger text-white shadow">

                        <div className="card-body text-center">

                            <h5>Unpaid Bills</h5>

                            <h2>{dashboard.unpaidBills}</h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-4 mb-3">

                    <div className="card bg-info text-white shadow">

                        <div className="card-body text-center">

                            <h5>Total Payments</h5>

                            <h2>{dashboard.totalPayments}</h2>

                        </div>

                    </div>

                </div>

            </div>

            <DashboardCharts
                paidBills={dashboard.paidBills}
                unpaidBills={dashboard.unpaidBills}
                revenue={dashboard.totalRevenue}
            />

            <div className="card mt-4 shadow">

                <div className="card-header bg-secondary text-white">

                    Project Information

                </div>

                <div className="card-body">

                    <p><strong>Project:</strong> Smart Water Billing System</p>

                    <p><strong>Technology:</strong> React + Spring Boot + PostgreSQL</p>

                    <p><strong>Status:</strong> Project Completed</p>

                </div>
                <div className="row mt-4">

                    <div className="col-md-4">

                        <div className="card shadow">

                            <div className="card-header bg-primary text-white">
                                Recent Consumers
                            </div>

                            <table className="table table-sm mb-0">

                                <thead>

                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                </tr>

                                </thead>

                                <tbody>

                                {recentConsumers.map(c => (

                                    <tr key={c.id}>
                                        <td>{c.id}</td>
                                        <td>{c.ownerName}</td>
                                    </tr>

                                ))}

                                </tbody>

                            </table>

                        </div>

                    </div>

                    <div className="col-md-4">

                        <div className="card shadow">

                            <div className="card-header bg-success text-white">
                                Recent Bills
                            </div>

                            <table className="table table-sm mb-0">

                                <thead>

                                <tr>
                                    <th>ID</th>
                                    <th>Total</th>
                                </tr>

                                </thead>

                                <tbody>

                                {recentBills.map(b => (

                                    <tr key={b.id}>
                                        <td>{b.id}</td>
                                        <td>₹ {b.totalAmount}</td>
                                    </tr>

                                ))}

                                </tbody>

                            </table>

                        </div>

                    </div>

                    <div className="col-md-4">

                        <div className="card shadow">

                            <div className="card-header bg-info text-white">
                                Recent Payments
                            </div>

                            <table className="table table-sm mb-0">

                                <thead>

                                <tr>
                                    <th>ID</th>
                                    <th>Amount</th>
                                </tr>

                                </thead>

                                <tbody>

                                {recentPayments.map(p => (

                                    <tr key={p.id}>
                                        <td>{p.id}</td>
                                        <td>₹ {p.amount}</td>
                                    </tr>

                                ))}

                                </tbody>

                            </table>

                        </div>

                    </div>

                </div>

            </div>

        </Layout>

    );

}

export default Dashboard;