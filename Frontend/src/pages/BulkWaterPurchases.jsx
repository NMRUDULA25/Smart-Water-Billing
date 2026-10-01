import { useEffect, useState } from "react";

import {
    getAllBulkWaterPurchases,
    createBulkWaterPurchase,
    deleteBulkWaterPurchase
} from "../services/bulkWaterPurchaseService";

function BulkWaterPurchases() {

    const [purchases, setPurchases] = useState([]);
    const [loading, setLoading] = useState(false);

    const [formData, setFormData] = useState({
        supplierName: "",
        quantity: "",
        pricePerUnit: "",
        purchaseDate: "",
        status: "PENDING"
    });

    // =========================================================
    // LOAD PURCHASES
    // =========================================================

    const loadPurchases = async () => {

        try {

            setLoading(true);

            const response = await getAllBulkWaterPurchases();

            console.log("========== BULK WATER API ==========");
            console.log("FULL API RESPONSE:", response);
            console.log("API DATA:", response?.data);

            let apiData = response?.data;

            // Handle all common Spring Boot response formats
            if (Array.isArray(apiData)) {
                apiData = apiData;
            }
            else if (apiData && Array.isArray(apiData.data)) {
                apiData = apiData.data;
            }
            else if (apiData && Array.isArray(apiData.content)) {
                apiData = apiData.content;
            }
            else {
                apiData = [];
            }

            /*
             * IMPORTANT:
             *
             * Backend currently gives us totalAmount and quantity.
             * Therefore calculate:
             *
             * pricePerUnit = totalAmount / quantity
             *
             * This fixes the ₹0.00 problem.
             */

            const normalizedData = apiData.map((purchase) => {

                const quantity = Number(
                    purchase.quantity ?? 0
                );

                const totalAmount = Number(
                    purchase.totalAmount ??
                    purchase.totalPrice ??
                    purchase.amount ??
                    0
                );

                // Try backend rate fields first
                let pricePerUnit = Number(
                    purchase.pricePerUnit ??
                    purchase.ratePerUnit ??
                    purchase.unitPrice ??
                    purchase.price ??
                    0
                );

                /*
                 * If backend does not return price/rate,
                 * calculate it from totalAmount / quantity.
                 */
                if (
                    (!pricePerUnit || pricePerUnit === 0) &&
                    quantity > 0 &&
                    totalAmount > 0
                ) {
                    pricePerUnit = totalAmount / quantity;
                }

                return {
                    ...purchase,

                    quantity: quantity,

                    totalAmount: totalAmount,

                    pricePerUnit: pricePerUnit
                };
            });

            console.log(
                "NORMALIZED PURCHASES:",
                normalizedData
            );

            if (normalizedData.length > 0) {

                console.log(
                    "FIRST PURCHASE:",
                    normalizedData[0]
                );

                console.log(
                    "CALCULATED PRICE PER UNIT:",
                    normalizedData[0].pricePerUnit
                );
            }

            setPurchases(normalizedData);

        }
        catch (error) {

            console.error(
                "ERROR LOADING BULK WATER PURCHASES:",
                error
            );

            setPurchases([]);

        }
        finally {

            setLoading(false);

        }
    };


    // =========================================================
    // INITIAL LOAD
    // =========================================================

    useEffect(() => {

        loadPurchases();

    }, []);


    // =========================================================
    // INPUT CHANGE
    // =========================================================

    const handleChange = (event) => {

        const {
            name,
            value
        } = event.target;

        setFormData((previous) => ({
            ...previous,
            [name]: value
        }));

    };


    // =========================================================
    // ESTIMATED TOTAL
    // =========================================================

    const calculateEstimatedTotal = () => {

        const quantity = Number(
            formData.quantity || 0
        );

        const price = Number(
            formData.pricePerUnit || 0
        );

        return (
            quantity * price
        ).toFixed(2);

    };


    // =========================================================
    // CREATE PURCHASE
    // =========================================================

    const handleSubmit = async (event) => {

        event.preventDefault();

        if (
            !formData.supplierName.trim() ||
            !formData.quantity ||
            !formData.pricePerUnit ||
            !formData.purchaseDate
        ) {

            alert(
                "Please fill all required fields."
            );

            return;

        }

        try {

            const quantity = Number(
                formData.quantity
            );

            const pricePerUnit = Number(
                formData.pricePerUnit
            );

            const totalAmount =
                quantity * pricePerUnit;

            const purchaseData = {

                supplierName:
                    formData.supplierName.trim(),

                quantity:
                quantity,

                pricePerUnit:
                pricePerUnit,

                totalAmount:
                totalAmount,

                purchaseDate:
                formData.purchaseDate,

                status:
                formData.status
            };

            console.log(
                "SENDING PURCHASE:",
                purchaseData
            );

            await createBulkWaterPurchase(
                purchaseData
            );

            alert(
                "Bulk water purchase created successfully!"
            );

            setFormData({
                supplierName: "",
                quantity: "",
                pricePerUnit: "",
                purchaseDate: "",
                status: "PENDING"
            });

            await loadPurchases();

        }
        catch (error) {

            console.error(
                "ERROR CREATING PURCHASE:",
                error
            );

            console.error(
                "BACKEND RESPONSE:",
                error?.response?.data
            );

            alert(
                error?.response?.data?.message ||
                "Failed to create bulk water purchase."
            );

        }

    };


    // =========================================================
    // DELETE
    // =========================================================

    const handleDelete = async (id) => {

        const confirmed =
            window.confirm(
                "Are you sure you want to delete this purchase?"
            );

        if (!confirmed) {
            return;
        }

        try {

            await deleteBulkWaterPurchase(id);

            alert(
                "Purchase deleted successfully!"
            );

            await loadPurchases();

        }
        catch (error) {

            console.error(
                "ERROR DELETING PURCHASE:",
                error
            );

            alert(
                "Failed to delete purchase."
            );

        }

    };


    // =========================================================
    // SUMMARY
    // =========================================================

    const totalPurchases =
        purchases.length;


    const totalQuantity =
        purchases.reduce(
            (sum, purchase) =>
                sum +
                Number(
                    purchase.quantity || 0
                ),
            0
        );


    const totalAmount =
        purchases.reduce(
            (sum, purchase) =>
                sum +
                Number(
                    purchase.totalAmount || 0
                ),
            0
        );


    const completedCount =
        purchases.filter(
            (purchase) =>
                String(
                    purchase.status || ""
                ).toUpperCase() === "COMPLETED"
        ).length;


    const pendingCount =
        purchases.filter(
            (purchase) =>
                String(
                    purchase.status || ""
                ).toUpperCase() === "PENDING"
        ).length;


    // =========================================================
    // STATUS BADGE
    // =========================================================

    const getStatusClass = (status) => {

        const value =
            String(status || "")
                .toUpperCase();

        if (value === "COMPLETED") {
            return "badge bg-success";
        }

        if (value === "CANCELLED") {
            return "badge bg-danger";
        }

        return "badge bg-warning text-dark";

    };


    // =========================================================
    // UI
    // =========================================================

    return (

        <div className="container-fluid py-2">

            {/* =================================================
                HEADER
            ================================================= */}

            <div className="d-flex justify-content-between align-items-center mb-3">

                <div>

                    <h2 className="mb-1">
                        Bulk Water Purchases
                    </h2>

                    <p className="text-muted mb-0">
                        Manage bulk water purchases from suppliers
                    </p>

                </div>

                <button
                    type="button"
                    className="btn btn-outline-primary"
                    onClick={loadPurchases}
                    disabled={loading}
                >
                    <i className="bi bi-arrow-clockwise me-1"></i>
                    Refresh
                </button>

            </div>


            {/* =================================================
                SUMMARY CARDS
            ================================================= */}

            <div className="row mb-4">

                {/* TOTAL PURCHASES */}

                <div className="col-md-3 mb-3">

                    <div className="card bg-primary text-white shadow h-100">

                        <div className="card-body">

                            <div className="d-flex justify-content-between">

                                <div>

                                    <h6>
                                        Total Purchases
                                    </h6>

                                    <h2 className="mb-0">
                                        {totalPurchases}
                                    </h2>

                                </div>

                                <i
                                    className="bi bi-cart-check"
                                    style={{
                                        fontSize: "40px"
                                    }}
                                ></i>

                            </div>

                        </div>

                    </div>

                </div>


                {/* TOTAL QUANTITY */}

                <div className="col-md-3 mb-3">

                    <div className="card bg-success text-white shadow h-100">

                        <div className="card-body">

                            <div className="d-flex justify-content-between">

                                <div>

                                    <h6>
                                        Total Quantity
                                    </h6>

                                    <h2 className="mb-0">
                                        {totalQuantity.toFixed(2)}
                                    </h2>

                                </div>

                                <i
                                    className="bi bi-droplet"
                                    style={{
                                        fontSize: "40px"
                                    }}
                                ></i>

                            </div>

                        </div>

                    </div>

                </div>


                {/* TOTAL AMOUNT */}

                <div className="col-md-3 mb-3">

                    <div className="card bg-warning text-dark shadow h-100">

                        <div className="card-body">

                            <div className="d-flex justify-content-between">

                                <div>

                                    <h6>
                                        Total Amount
                                    </h6>

                                    <h2 className="mb-0">
                                        ₹ {totalAmount.toFixed(2)}
                                    </h2>

                                </div>

                                <i
                                    className="bi bi-currency-rupee"
                                    style={{
                                        fontSize: "40px"
                                    }}
                                ></i>

                            </div>

                        </div>

                    </div>

                </div>


                {/* COMPLETED */}

                <div className="col-md-3 mb-3">

                    <div className="card bg-dark text-white shadow h-100">

                        <div className="card-body">

                            <div className="d-flex justify-content-between">

                                <div>

                                    <h6>
                                        Completed
                                    </h6>

                                    <h2 className="mb-1">
                                        {completedCount}
                                    </h2>

                                    <small>
                                        Pending: {pendingCount}
                                    </small>

                                </div>

                                <i
                                    className="bi bi-check-circle"
                                    style={{
                                        fontSize: "40px"
                                    }}
                                ></i>

                            </div>

                        </div>

                    </div>

                </div>

            </div>


            {/* =================================================
                ADD PURCHASE
            ================================================= */}

            <div className="card shadow mb-4">

                <div className="card-header bg-primary text-white">

                    <h5 className="mb-0">

                        <i className="bi bi-plus-circle me-2"></i>

                        Add Bulk Water Purchase

                    </h5>

                </div>


                <div className="card-body">

                    <form onSubmit={handleSubmit}>

                        <div className="row">

                            {/* SUPPLIER */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">

                                    Supplier Name
                                    <span className="text-danger">
                                        *
                                    </span>

                                </label>

                                <input
                                    type="text"
                                    className="form-control"
                                    name="supplierName"
                                    value={
                                        formData.supplierName
                                    }
                                    onChange={
                                        handleChange
                                    }
                                    placeholder="Enter supplier name"
                                />

                            </div>


                            {/* QUANTITY */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">

                                    Quantity
                                    <span className="text-danger">
                                        *
                                    </span>

                                </label>

                                <input
                                    type="number"
                                    className="form-control"
                                    name="quantity"
                                    value={
                                        formData.quantity
                                    }
                                    onChange={
                                        handleChange
                                    }
                                    placeholder="Enter quantity"
                                    min="0"
                                    step="0.01"
                                />

                            </div>


                            {/* PRICE */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">

                                    Price Per Unit
                                    <span className="text-danger">
                                        *
                                    </span>

                                </label>

                                <input
                                    type="number"
                                    className="form-control"
                                    name="pricePerUnit"
                                    value={
                                        formData.pricePerUnit
                                    }
                                    onChange={
                                        handleChange
                                    }
                                    placeholder="Enter price per unit"
                                    min="0"
                                    step="0.01"
                                />

                            </div>


                            {/* DATE */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">

                                    Purchase Date
                                    <span className="text-danger">
                                        *
                                    </span>

                                </label>

                                <input
                                    type="date"
                                    className="form-control"
                                    name="purchaseDate"
                                    value={
                                        formData.purchaseDate
                                    }
                                    onChange={
                                        handleChange
                                    }
                                />

                            </div>


                            {/* STATUS */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">
                                    Status
                                </label>

                                <select
                                    className="form-select"
                                    name="status"
                                    value={
                                        formData.status
                                    }
                                    onChange={
                                        handleChange
                                    }
                                >

                                    <option value="PENDING">
                                        PENDING
                                    </option>

                                    <option value="COMPLETED">
                                        COMPLETED
                                    </option>

                                    <option value="PAID">
                                        PAID
                                    </option>

                                    <option value="CANCELLED">
                                        CANCELLED
                                    </option>

                                </select>

                            </div>


                            {/* ESTIMATED TOTAL */}

                            <div className="col-md-4 mb-3">

                                <label className="form-label">
                                    Estimated Total
                                </label>

                                <input
                                    type="text"
                                    className="form-control"
                                    value={
                                        `₹ ${calculateEstimatedTotal()}`
                                    }
                                    readOnly
                                />

                            </div>

                        </div>


                        <button
                            type="submit"
                            className="btn btn-success"
                        >

                            <i className="bi bi-plus-circle me-2"></i>

                            Add Purchase

                        </button>

                    </form>

                </div>

            </div>


            {/* =================================================
                PURCHASE TABLE
            ================================================= */}

            <div className="card shadow">

                <div className="card-header bg-dark text-white d-flex justify-content-between align-items-center">

                    <h5 className="mb-0">

                        <i className="bi bi-table me-2"></i>

                        Bulk Water Purchase Records

                    </h5>

                    <span className="badge bg-light text-dark">

                        {purchases.length} Records

                    </span>

                </div>


                <div className="card-body">

                    {loading ? (

                        <div className="text-center p-4">

                            <div
                                className="spinner-border text-primary"
                                role="status"
                            ></div>

                            <p className="mt-2">
                                Loading purchases...
                            </p>

                        </div>

                    ) : purchases.length === 0 ? (

                        <div className="alert alert-info">
                            No bulk water purchases found.
                        </div>

                    ) : (

                        <div className="table-responsive">

                            <table className="table table-bordered table-hover align-middle">

                                <thead className="table-dark">

                                <tr>

                                    <th>ID</th>

                                    <th>Supplier</th>

                                    <th>Quantity</th>

                                    <th>Price / Unit</th>

                                    <th>Total Amount</th>

                                    <th>Purchase Date</th>

                                    <th>Status</th>

                                    <th>Action</th>

                                </tr>

                                </thead>


                                <tbody>

                                {purchases.map(
                                    (purchase) => {

                                        const quantity =
                                            Number(
                                                purchase.quantity || 0
                                            );

                                        const total =
                                            Number(
                                                purchase.totalAmount || 0
                                            );

                                        /*
                                         * FINAL FIX:
                                         * Always calculate unit price
                                         * from total amount / quantity
                                         * when backend doesn't provide it.
                                         */

                                        const unitPrice =
                                            quantity > 0
                                                ? total / quantity
                                                : 0;

                                        return (

                                            <tr
                                                key={
                                                    purchase.id
                                                }
                                            >

                                                <td>
                                                    {purchase.id}
                                                </td>

                                                <td>
                                                    <strong>
                                                        {
                                                            purchase.supplierName ||
                                                            "-"
                                                        }
                                                    </strong>
                                                </td>

                                                <td>
                                                    {quantity.toFixed(2)}
                                                </td>

                                                <td>
                                                    ₹{" "}
                                                    {unitPrice.toFixed(2)}
                                                </td>

                                                <td>
                                                    <strong>
                                                        ₹{" "}
                                                        {total.toFixed(2)}
                                                    </strong>
                                                </td>

                                                <td>
                                                    {
                                                        purchase.purchaseDate ||
                                                        "-"
                                                    }
                                                </td>

                                                <td>

                                                    <span
                                                        className={
                                                            getStatusClass(
                                                                purchase.status
                                                            )
                                                        }
                                                    >

                                                        {
                                                            purchase.status ||
                                                            "PENDING"
                                                        }

                                                    </span>

                                                </td>

                                                <td>

                                                    <button
                                                        type="button"
                                                        className="btn btn-sm btn-danger"
                                                        onClick={() =>
                                                            handleDelete(
                                                                purchase.id
                                                            )
                                                        }
                                                    >

                                                        <i className="bi bi-trash me-1"></i>

                                                        Delete

                                                    </button>

                                                </td>

                                            </tr>

                                        );

                                    }
                                )}

                                </tbody>

                            </table>

                        </div>

                    )}

                </div>

            </div>

        </div>

    );

}

export default BulkWaterPurchases;