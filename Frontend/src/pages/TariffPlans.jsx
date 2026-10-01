import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import axios from "axios";

function TariffPlans() {

    const [plans, setPlans] = useState([]);

    const [formData, setFormData] = useState({
        planName: "",
        baseRate: "",
        higherRate: "",
        baseLimit: ""
    });

    const [editingId, setEditingId] = useState(null);

    const API_URL = "http://localhost:8080/api/tariff-plans";

    // Load tariff plans
    const loadPlans = async () => {

        try {

            const response = await axios.get(API_URL);

            setPlans(response.data);

        } catch (error) {

            console.error("Error loading tariff plans:", error);

            alert("Failed to load tariff plans");

        }

    };

    useEffect(() => {

        loadPlans();

    }, []);

    // Handle input
    const handleChange = (e) => {

        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });

    };

    // Reset form
    const resetForm = () => {

        setFormData({
            planName: "",
            baseRate: "",
            higherRate: "",
            baseLimit: ""
        });

        setEditingId(null);

    };

    // Add / Update tariff plan
    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const data = {
                planName: formData.planName,
                baseRate: Number(formData.baseRate),
                higherRate: Number(formData.higherRate),
                baseLimit: Number(formData.baseLimit)
            };

            if (editingId) {

                await axios.put(
                    `${API_URL}/${editingId}`,
                    data
                );

                alert("Tariff Plan updated successfully");

            } else {

                await axios.post(
                    API_URL,
                    data
                );

                alert("Tariff Plan created successfully");

            }

            resetForm();

            loadPlans();

        } catch (error) {

            console.error("Error saving tariff plan:", error);

            alert("Failed to save tariff plan");

        }

    };

    // Edit tariff plan
    const handleEdit = (plan) => {

        setEditingId(plan.id);

        setFormData({
            planName: plan.planName,
            baseRate: plan.baseRate,
            higherRate: plan.higherRate,
            baseLimit: plan.baseLimit
        });

        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });

    };

    // Delete tariff plan
    const handleDelete = async (id) => {

        if (!window.confirm("Delete this tariff plan?")) {
            return;
        }

        try {

            await axios.delete(`${API_URL}/${id}`);

            alert("Tariff Plan deleted successfully");

            loadPlans();

        } catch (error) {

            console.error("Error deleting tariff plan:", error);

            alert("Failed to delete tariff plan");

        }

    };

    return (

        <Layout>

            <h2 className="mb-4">
                Tariff Plans
            </h2>

            {/* Tariff Plan Form */}

            <div className="card shadow mb-4">

                <div className="card-header bg-primary text-white">

                    <h5 className="mb-0">

                        {editingId
                            ? "Update Tariff Plan"
                            : "Add Tariff Plan"}

                    </h5>

                </div>

                <div className="card-body">

                    <form onSubmit={handleSubmit}>

                        <div className="row">

                            <div className="col-md-3 mb-3">

                                <label className="form-label">
                                    Plan Name
                                </label>

                                <input
                                    type="text"
                                    className="form-control"
                                    name="planName"
                                    value={formData.planName}
                                    onChange={handleChange}
                                    placeholder="Domestic Standard"
                                    required
                                />

                            </div>

                            <div className="col-md-3 mb-3">

                                <label className="form-label">
                                    Base Rate
                                </label>

                                <input
                                    type="number"
                                    step="0.01"
                                    className="form-control"
                                    name="baseRate"
                                    value={formData.baseRate}
                                    onChange={handleChange}
                                    placeholder="10"
                                    required
                                />

                            </div>

                            <div className="col-md-3 mb-3">

                                <label className="form-label">
                                    Higher Rate
                                </label>

                                <input
                                    type="number"
                                    step="0.01"
                                    className="form-control"
                                    name="higherRate"
                                    value={formData.higherRate}
                                    onChange={handleChange}
                                    placeholder="15"
                                    required
                                />

                            </div>

                            <div className="col-md-3 mb-3">

                                <label className="form-label">
                                    Base Limit
                                </label>

                                <input
                                    type="number"
                                    step="0.01"
                                    className="form-control"
                                    name="baseLimit"
                                    value={formData.baseLimit}
                                    onChange={handleChange}
                                    placeholder="10"
                                    required
                                />

                            </div>

                        </div>

                        <button
                            type="submit"
                            className="btn btn-success me-2"
                        >

                            {editingId
                                ? "Update Plan"
                                : "Add Plan"}

                        </button>

                        {editingId && (

                            <button
                                type="button"
                                className="btn btn-secondary"
                                onClick={resetForm}
                            >
                                Cancel
                            </button>

                        )}

                    </form>

                </div>

            </div>

            {/* Tariff Plans Table */}

            <div className="card shadow">

                <div className="card-header bg-dark text-white">

                    <h5 className="mb-0">
                        Available Tariff Plans
                    </h5>

                </div>

                <div className="card-body">

                    <div className="table-responsive">

                        <table className="table table-bordered table-striped">

                            <thead className="table-dark">

                            <tr>

                                <th>ID</th>

                                <th>Plan Name</th>

                                <th>Base Rate</th>

                                <th>Higher Rate</th>

                                <th>Base Limit</th>

                                <th>Action</th>

                            </tr>

                            </thead>

                            <tbody>

                            {plans.length === 0 ? (

                                <tr>

                                    <td
                                        colSpan="6"
                                        className="text-center"
                                    >
                                        No tariff plans found
                                    </td>

                                </tr>

                            ) : (

                                plans.map((plan) => (

                                    <tr key={plan.id}>

                                        <td>
                                            {plan.id}
                                        </td>

                                        <td>
                                            {plan.planName}
                                        </td>

                                        <td>
                                            ₹ {plan.baseRate}
                                        </td>

                                        <td>
                                            ₹ {plan.higherRate}
                                        </td>

                                        <td>
                                            {plan.baseLimit} KL
                                        </td>

                                        <td>

                                            <button
                                                className="btn btn-warning btn-sm me-2"
                                                onClick={() =>
                                                    handleEdit(plan)
                                                }
                                            >
                                                Edit
                                            </button>

                                            <button
                                                className="btn btn-danger btn-sm"
                                                onClick={() =>
                                                    handleDelete(plan.id)
                                                }
                                            >
                                                Delete
                                            </button>

                                        </td>

                                    </tr>

                                ))

                            )}

                            </tbody>

                        </table>

                    </div>

                </div>

            </div>

        </Layout>

    );

}

export default TariffPlans;