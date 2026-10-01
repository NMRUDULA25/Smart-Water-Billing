import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Layout from "../components/Layout";

import {
    getAllPayments,
    createPayment,
    deletePayment
} from "../services/paymentService";

import { markBillPaid } from "../services/billService";

function Payments() {

    const location = useLocation();
    const navigate = useNavigate();

    const [payments, setPayments] = useState([]);
    const [search, setSearch] = useState("");
    const [statusFilter, setStatusFilter] = useState("ALL");

    const [formData, setFormData] = useState({
        billId: "",
        consumerNumber: "",
        amount: "",
        paymentDate: "",
        paymentMethod: "",
        status: "SUCCESS"
    });

    useEffect(() => {

        loadPayments();

        if (location.state) {

            setFormData({
                billId: location.state.billId,
                consumerNumber: location.state.consumerNumber,
                amount: location.state.amount,
                paymentDate: "",
                paymentMethod: "",
                status: "SUCCESS"
            });

        }

    }, []);

    const loadPayments = async () => {

        try {

            const response = await getAllPayments();
            setPayments(response.data);

        } catch (error) {

            console.log(error);

        }

    };

    const handleChange = (e) => {

        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await createPayment(formData);

            await markBillPaid(formData.billId);

            alert("Payment Successful");

            loadPayments();

            navigate("/bills");

        } catch (error) {

            console.log(error);
            alert("Payment Failed");

        }

    };

    const handleDelete = async (id) => {

        if (!window.confirm("Delete this payment?")) return;

        await deletePayment(id);

        loadPayments();

    };

    const filteredPayments = payments.filter((payment) => {

        const matchesSearch =
            payment.consumerNumber
                .toLowerCase()
                .includes(search.toLowerCase());

        const matchesStatus =
            statusFilter === "ALL" ||
            payment.status === statusFilter;

        return matchesSearch && matchesStatus;

    });

    const totalRevenue = payments
        .filter((p) => p.status === "SUCCESS")
        .reduce((sum, p) => sum + p.amount, 0);

    const successfulPayments = payments.filter(
        (p) => p.status === "SUCCESS"
    ).length;

    return (

        <Layout>

            <h2 className="mb-4">Payments</h2>

            <div className="row mb-4">

                <div className="col-md-4">

                    <div className="card bg-primary text-white">

                        <div className="card-body text-center">

                            <h5>Total Payments</h5>

                            <h2>{payments.length}</h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-4">

                    <div className="card bg-success text-white">

                        <div className="card-body text-center">

                            <h5>Total Revenue</h5>

                            <h2>₹ {totalRevenue}</h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-4">

                    <div className="card bg-info text-white">

                        <div className="card-body text-center">

                            <h5>Successful Payments</h5>

                            <h2>{successfulPayments}</h2>

                        </div>

                    </div>

                </div>

            </div>

            <div className="card mb-4">

                <div className="card-header bg-success text-white">

                    Make Payment

                </div>

                <div className="card-body">

                    <form onSubmit={handleSubmit}>

                        <div className="row">

                            <div className="col-md-4 mb-3">

                                <input
                                    type="number"
                                    className="form-control"
                                    value={formData.billId}
                                    readOnly
                                />

                            </div>

                            <div className="col-md-4 mb-3">

                                <input
                                    className="form-control"
                                    value={formData.consumerNumber}
                                    readOnly
                                />

                            </div>

                            <div className="col-md-4 mb-3">

                                <input
                                    className="form-control"
                                    value={formData.amount}
                                    readOnly
                                />

                            </div>

                            <div className="col-md-4 mb-3">

                                <input
                                    type="date"
                                    className="form-control"
                                    name="paymentDate"
                                    value={formData.paymentDate}
                                    onChange={handleChange}
                                    required
                                />

                            </div>

                            <div className="col-md-4 mb-3">

                                <select
                                    className="form-select"
                                    name="paymentMethod"
                                    value={formData.paymentMethod}
                                    onChange={handleChange}
                                    required
                                >

                                    <option value="">Select Method</option>
                                    <option value="Cash">Cash</option>
                                    <option value="UPI">UPI</option>
                                    <option value="Card">Card</option>
                                    <option value="Net Banking">Net Banking</option>

                                </select>

                            </div>

                            <div className="col-md-2 mb-3">

                                <select
                                    className="form-select"
                                    name="status"
                                    value={formData.status}
                                    onChange={handleChange}
                                >

                                    <option value="SUCCESS">SUCCESS</option>
                                    <option value="FAILED">FAILED</option>
                                    <option value="PENDING">PENDING</option>

                                </select>

                            </div>

                            <div className="col-md-2 mb-3">

                                <button className="btn btn-success w-100">

                                    Pay

                                </button>

                            </div>

                        </div>

                    </form>

                </div>

            </div>

            <div className="row mb-3">

                <div className="col-md-4">

                    <input
                        className="form-control"
                        placeholder="Search Consumer Number"
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                    />

                </div>

                <div className="col-md-3">

                    <select
                        className="form-select"
                        value={statusFilter}
                        onChange={(e) => setStatusFilter(e.target.value)}
                    >

                        <option value="ALL">All Status</option>
                        <option value="SUCCESS">SUCCESS</option>
                        <option value="FAILED">FAILED</option>
                        <option value="PENDING">PENDING</option>

                    </select>

                </div>

            </div>

            <table className="table table-bordered table-striped">

                <thead className="table-dark">

                <tr>

                    <th>ID</th>
                    <th>Bill ID</th>
                    <th>Consumer</th>
                    <th>Amount</th>
                    <th>Date</th>
                    <th>Method</th>
                    <th>Status</th>
                    <th>Action</th>

                </tr>

                </thead>

                <tbody>

                {filteredPayments.length === 0 ? (

                    <tr>

                        <td colSpan="8" className="text-center">
                            No Payments Found
                        </td>

                    </tr>

                ) : (

                    filteredPayments.map((payment) => (

                        <tr key={payment.id}>

                            <td>{payment.id}</td>
                            <td>{payment.billId}</td>
                            <td>{payment.consumerNumber}</td>
                            <td>₹ {payment.amount}</td>
                            <td>{payment.paymentDate}</td>
                            <td>{payment.paymentMethod}</td>
                            <td>{payment.status}</td>

                            <td>

                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => handleDelete(payment.id)}
                                >
                                    Delete
                                </button>

                            </td>

                        </tr>

                    ))

                )}

                </tbody>

            </table>

        </Layout>

    );

}

export default Payments;