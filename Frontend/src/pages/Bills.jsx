import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Layout from "../components/Layout";

import {
    getAllBills,
    createBill,
    deleteBill
} from "../services/billService";

function Bills() {

    const navigate = useNavigate();

    const [bills, setBills] = useState([]);
    const [tariffPlans, setTariffPlans] = useState([]);

    const [search, setSearch] = useState("");
    const [statusFilter, setStatusFilter] = useState("ALL");

    const [loadingPlans, setLoadingPlans] = useState(true);

    const [formData, setFormData] = useState({
        consumerNumber: "",
        unitsConsumed: "",
        tariffPlanId: "",
        billDate: "",
        billingCycle: "",
        status: "UNPAID"
    });

    // ==============================
    // LOAD BILLS
    // ==============================

    useEffect(() => {
        loadBills();
        loadTariffPlans();
    }, []);

    const loadBills = async () => {

        try {

            const response = await getAllBills();

            setBills(response.data);

        } catch (error) {

            console.error("Error loading bills:", error);

            alert("Failed to load bills.");

        }

    };

    // ==============================
    // LOAD TARIFF PLANS
    // ==============================

    const loadTariffPlans = async () => {

        try {

            setLoadingPlans(true);

            const response = await fetch(
                "http://localhost:8080/api/tariff-plans"
            );

            if (!response.ok) {
                throw new Error("Failed to load tariff plans");
            }

            const data = await response.json();

            setTariffPlans(data);

        } catch (error) {

            console.error("Error loading tariff plans:", error);

            alert("Failed to load tariff plans.");

        } finally {

            setLoadingPlans(false);

        }

    };

    // ==============================
    // HANDLE FORM CHANGE
    // ==============================

    const handleChange = (e) => {

        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });

    };

    // ==============================
    // CREATE BILL
    // ==============================

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            if (!formData.consumerNumber.trim()) {

                alert("Please enter Consumer Number.");

                return;
            }

            if (!formData.unitsConsumed || Number(formData.unitsConsumed) <= 0) {

                alert("Please enter valid Units Consumed.");

                return;
            }

            if (!formData.tariffPlanId) {

                alert("Please select a Tariff Plan.");

                return;
            }

            if (!formData.billDate) {

                alert("Please select Bill Date.");

                return;
            }

            if (!formData.billingCycle.trim()) {

                alert("Please enter Billing Cycle.");

                return;
            }

            // Send tariffPlanId to backend.
            // Backend calculates rate and total amount.
            const billData = {
                consumerNumber: formData.consumerNumber,
                unitsConsumed: Number(formData.unitsConsumed),
                tariffPlanId: Number(formData.tariffPlanId),
                billDate: formData.billDate,
                billingCycle: formData.billingCycle,
                status: "UNPAID"
            };

            await createBill(billData);

            alert("Bill Generated Successfully");

            // Reset form
            setFormData({
                consumerNumber: "",
                unitsConsumed: "",
                tariffPlanId: "",
                billDate: "",
                billingCycle: "",
                status: "UNPAID"
            });

            // Reload bills
            loadBills();

        } catch (error) {

            console.error("Error creating bill:", error);

            if (error.response?.data?.message) {

                alert(error.response.data.message);

            } else {

                alert("Failed to generate bill.");

            }

        }

    };

    // ==============================
    // DELETE BILL
    // ==============================

    const handleDelete = async (id) => {

        if (!window.confirm("Delete Bill?")) {
            return;
        }

        try {

            await deleteBill(id);

            alert("Bill deleted successfully.");

            loadBills();

        } catch (error) {

            console.error("Error deleting bill:", error);

            alert("Failed to delete bill.");

        }

    };

    // ==============================
    // GO TO PAYMENT
    // ==============================

    const handlePaid = (bill) => {

        navigate("/payments", {

            state: {

                billId: bill.id,

                consumerNumber: bill.consumerNumber,

                amount: bill.totalAmount

            }

        });

    };

    // ==============================
    // FILTER BILLS
    // ==============================

    const filteredBills = bills.filter((bill) => {

        const matchesSearch =
            bill.consumerNumber
                ?.toLowerCase()
                .includes(search.toLowerCase());

        const matchesStatus =
            statusFilter === "ALL" ||
            bill.status === statusFilter;

        return matchesSearch && matchesStatus;

    });

    // ==============================
    // TOTAL REVENUE
    // ==============================

    const totalRevenue = bills
        .filter((b) => b.status === "PAID")
        .reduce(
            (sum, b) => sum + Number(b.totalAmount || 0),
            0
        );

    // ==============================
    // UNPAID BILLS
    // ==============================

    const unpaidBills = bills.filter(
        (b) => b.status === "UNPAID"
    ).length;

    // ==============================
    // FIND SELECTED TARIFF PLAN
    // ==============================

    const selectedTariffPlan = tariffPlans.find(
        (plan) =>
            Number(plan.id) === Number(formData.tariffPlanId)
    );

    // ==============================
    // PREVIEW TOTAL
    // ==============================

    let estimatedTotal = 0;

    if (
        selectedTariffPlan &&
        formData.unitsConsumed &&
        Number(formData.unitsConsumed) > 0
    ) {

        const units = Number(formData.unitsConsumed);

        const baseLimit = Number(selectedTariffPlan.baseLimit);

        const baseRate = Number(selectedTariffPlan.baseRate);

        const higherRate = Number(selectedTariffPlan.higherRate);

        if (units <= baseLimit) {

            estimatedTotal = units * baseRate;

        } else {

            estimatedTotal =
                (baseLimit * baseRate) +
                ((units - baseLimit) * higherRate);

        }

    }

    // ==============================
    // UI
    // ==============================

    return (

        <Layout>

            <h2 className="mb-4">
                Water Bills
            </h2>

            {/* ==============================
                SUMMARY CARDS
            ============================== */}

            <div className="row mb-4">

                <div className="col-md-4">

                    <div className="card bg-primary text-white">

                        <div className="card-body text-center">

                            <h5>Total Bills</h5>

                            <h2>
                                {bills.length}
                            </h2>

                        </div>

                    </div>

                </div>


                <div className="col-md-4">

                    <div className="card bg-success text-white">

                        <div className="card-body text-center">

                            <h5>
                                Total Revenue
                            </h5>

                            <h2>
                                ₹ {totalRevenue}
                            </h2>

                        </div>

                    </div>

                </div>


                <div className="col-md-4">

                    <div className="card bg-danger text-white">

                        <div className="card-body text-center">

                            <h5>
                                Unpaid Bills
                            </h5>

                            <h2>
                                {unpaidBills}
                            </h2>

                        </div>

                    </div>

                </div>

            </div>


            {/* ==============================
                GENERATE BILL
            ============================== */}

            <div className="card mb-4">

                <div className="card-header bg-primary text-white">

                    <h5 className="mb-0">
                        Generate Bill
                    </h5>

                </div>


                <div className="card-body">

                    <form onSubmit={handleSubmit}>

                        <div className="row">


                            {/* CONSUMER NUMBER */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">
                                    Consumer Number
                                </label>

                                <input
                                    className="form-control"
                                    placeholder="Consumer Number"
                                    name="consumerNumber"
                                    value={formData.consumerNumber}
                                    onChange={handleChange}
                                    required
                                />

                            </div>


                            {/* UNITS */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">
                                    Units Consumed
                                </label>

                                <input
                                    type="number"
                                    min="0"
                                    step="0.01"
                                    className="form-control"
                                    placeholder="Units Consumed"
                                    name="unitsConsumed"
                                    value={formData.unitsConsumed}
                                    onChange={handleChange}
                                    required
                                />

                            </div>


                            {/* TARIFF PLAN */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">
                                    Tariff Plan
                                </label>

                                <select
                                    className="form-select"
                                    name="tariffPlanId"
                                    value={formData.tariffPlanId}
                                    onChange={handleChange}
                                    required
                                >

                                    <option value="">
                                        {loadingPlans
                                            ? "Loading Tariff Plans..."
                                            : "Select Tariff Plan"}
                                    </option>

                                    {tariffPlans.map((plan) => (

                                        <option
                                            key={plan.id}
                                            value={plan.id}
                                        >

                                            {plan.planName}
                                            {" - ₹"}
                                            {plan.baseRate}
                                            {" / "}
                                            {plan.baseLimit}
                                            {" KL, Higher ₹"}
                                            {plan.higherRate}

                                        </option>

                                    ))}

                                </select>

                            </div>


                            {/* BILL DATE */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">
                                    Bill Date
                                </label>

                                <input
                                    type="date"
                                    className="form-control"
                                    name="billDate"
                                    value={formData.billDate}
                                    onChange={handleChange}
                                    required
                                />

                            </div>


                            {/* BILLING CYCLE */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">
                                    Billing Cycle
                                </label>

                                <input
                                    className="form-control"
                                    placeholder="Example: August 2026"
                                    name="billingCycle"
                                    value={formData.billingCycle}
                                    onChange={handleChange}
                                    required
                                />

                            </div>


                            {/* ESTIMATED TOTAL */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">
                                    Estimated Amount
                                </label>

                                <div className="form-control bg-light">

                                    {estimatedTotal > 0
                                        ? `₹ ${estimatedTotal.toFixed(2)}`
                                        : "Will be calculated automatically"}

                                </div>

                            </div>


                            {/* SAVE BUTTON */}

                            <div className="col-md-12">

                                <button
                                    type="submit"
                                    className="btn btn-primary w-100"
                                >

                                    Save Bill

                                </button>

                            </div>

                        </div>

                    </form>

                </div>

            </div>


            {/* ==============================
                SEARCH AND FILTER
            ============================== */}

            <div className="row mb-3">

                <div className="col-md-4">

                    <input
                        className="form-control"
                        placeholder="Search Consumer Number"
                        value={search}
                        onChange={(e) =>
                            setSearch(e.target.value)
                        }
                    />

                </div>


                <div className="col-md-3">

                    <select
                        className="form-select"
                        value={statusFilter}
                        onChange={(e) =>
                            setStatusFilter(e.target.value)
                        }
                    >

                        <option value="ALL">
                            All Status
                        </option>

                        <option value="PAID">
                            Paid
                        </option>

                        <option value="UNPAID">
                            Unpaid
                        </option>

                    </select>

                </div>

            </div>


            {/* ==============================
                BILLS TABLE
            ============================== */}

            <div className="table-responsive">

                <table className="table table-bordered table-striped">

                    <thead className="table-dark">

                    <tr>

                        <th>ID</th>

                        <th>Consumer</th>

                        <th>Units</th>

                        <th>Rate</th>

                        <th>Total</th>

                        <th>Date</th>

                        <th>Cycle</th>

                        <th>Status</th>

                        <th>Action</th>

                    </tr>

                    </thead>


                    <tbody>

                    {filteredBills.length === 0 ? (

                        <tr>

                            <td
                                colSpan="9"
                                className="text-center"
                            >

                                No Bills Found

                            </td>

                        </tr>

                    ) : (

                        filteredBills.map((bill) => (

                            <tr key={bill.id}>

                                <td>
                                    {bill.id}
                                </td>

                                <td>
                                    {bill.consumerNumber}
                                </td>

                                <td>
                                    {bill.unitsConsumed}
                                </td>

                                <td>
                                    ₹ {bill.ratePerUnit}
                                </td>

                                <td>
                                    ₹ {bill.totalAmount}
                                </td>

                                <td>
                                    {bill.billDate}
                                </td>

                                <td>
                                    {bill.billingCycle}
                                </td>

                                <td>

                                        <span
                                            className={
                                                bill.status === "PAID"
                                                    ? "badge bg-success"
                                                    : "badge bg-danger"
                                            }
                                        >

                                            {bill.status}

                                        </span>

                                </td>


                                <td>

                                    {bill.status === "UNPAID" && (

                                        <button
                                            className="btn btn-success btn-sm me-2"
                                            onClick={() =>
                                                handlePaid(bill)
                                            }
                                        >

                                            Pay

                                        </button>

                                    )}


                                    <button
                                        className="btn btn-danger btn-sm"
                                        onClick={() =>
                                            handleDelete(bill.id)
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

        </Layout>

    );

}

export default Bills;