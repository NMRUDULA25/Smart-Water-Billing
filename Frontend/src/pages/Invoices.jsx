import { useEffect, useState } from "react";
import Layout from "../components/Layout";

import {
    getAllInvoices,
    getInvoiceById,
    getInvoiceByWaterBillId
} from "../services/invoiceService";

import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

function Invoices() {

    // ==============================
    // STATE
    // ==============================

    const [invoices, setInvoices] = useState([]);

    const [search, setSearch] = useState("");

    const [statusFilter, setStatusFilter] = useState("ALL");

    const [loading, setLoading] = useState(true);

    const [selectedInvoice, setSelectedInvoice] = useState(null);


    // ==============================
    // LOAD INVOICES
    // ==============================

    useEffect(() => {

        loadInvoices();

    }, []);


    const loadInvoices = async () => {

        try {

            setLoading(true);

            const response = await getAllInvoices();

            console.log("Invoices:", response.data);

            setInvoices(response.data);

        } catch (error) {

            console.error("Error loading invoices:", error);

            alert("Failed to load invoices.");

        } finally {

            setLoading(false);

        }

    };


    // ==============================
    // VIEW INVOICE
    // ==============================

    const handleViewInvoice = async (invoice) => {

        try {

            let response;

            /*
             * Your current backend response contains:
             *
             * waterBillId: 8
             * id: null
             *
             * Therefore use waterBillId when invoice id is null.
             */

            if (invoice.id !== null && invoice.id !== undefined) {

                response = await getInvoiceById(invoice.id);

            } else if (
                invoice.waterBillId !== null &&
                invoice.waterBillId !== undefined
            ) {

                response = await getInvoiceByWaterBillId(
                    invoice.waterBillId
                );

            } else {

                setSelectedInvoice(invoice);

                return;
            }

            setSelectedInvoice(response.data);

        } catch (error) {

            console.error(
                "Error loading invoice details:",
                error
            );

            /*
             * If details API is not available,
             * still show the invoice we already have.
             */

            setSelectedInvoice(invoice);

        }

    };


    // ==============================
    // CLOSE INVOICE DETAILS
    // ==============================

    const closeInvoiceDetails = () => {

        setSelectedInvoice(null);

    };


    // ==============================
    // DOWNLOAD PDF
    // ==============================

    const downloadInvoice = (invoice) => {

        const doc = new jsPDF();


        // ==============================
        // HEADER
        // ==============================

        doc.setFontSize(20);

        doc.setFont("helvetica", "bold");

        doc.text(
            "SMART WATER BILLING SYSTEM",
            105,
            20,
            {
                align: "center"
            }
        );


        doc.setFontSize(16);

        doc.text(
            "WATER INVOICE",
            105,
            32,
            {
                align: "center"
            }
        );


        doc.setFont("helvetica", "normal");


        // ==============================
        // INVOICE INFORMATION
        // ==============================

        doc.setFontSize(11);

        const invoiceNumber =
            invoice.id !== null &&
            invoice.id !== undefined
                ? invoice.id
                : invoice.waterBillId;


        doc.text(
            `Invoice Number: INV-${invoiceNumber}`,
            20,
            50
        );


        doc.text(
            `Consumer Number: ${
                invoice.consumerNumber || "-"
            }`,
            20,
            58
        );


        doc.text(
            `Billing Cycle: ${
                invoice.billingCycle || "-"
            }`,
            20,
            66
        );


        doc.text(
            `Invoice Date: ${
                invoice.billDate || "-"
            }`,
            20,
            74
        );


        doc.text(
            `Status: ${
                invoice.status || "UNPAID"
            }`,
            20,
            82
        );


        // ==============================
        // INVOICE TABLE
        // ==============================

        autoTable(doc, {

            startY: 92,

            head: [
                [
                    "Description",
                    "Value"
                ]
            ],

            body: [

                [
                    "Water Consumption",
                    `${invoice.unitsConsumed || 0} units`
                ],

                [
                    "Rate Per Unit",
                    `Rs. ${
                        Number(
                            invoice.ratePerUnit || 0
                        ).toFixed(2)
                    }`
                ],

                [
                    "Total Amount",
                    `Rs. ${
                        Number(
                            invoice.totalAmount || 0
                        ).toFixed(2)
                    }`
                ]

            ],

            theme: "grid",

            styles: {
                fontSize: 11
            }

        });


        // ==============================
        // TOTAL
        // ==============================

        const finalY =
            doc.lastAutoTable &&
            doc.lastAutoTable.finalY
                ? doc.lastAutoTable.finalY
                : 130;


        doc.setFontSize(14);

        doc.setFont("helvetica", "bold");

        doc.text(
            `TOTAL AMOUNT: Rs. ${
                Number(
                    invoice.totalAmount || 0
                ).toFixed(2)
            }`,
            20,
            finalY + 20
        );


        // ==============================
        // PAYMENT INSTRUCTIONS
        // ==============================

        doc.setFontSize(12);

        doc.text(
            "Payment Instructions",
            20,
            finalY + 40
        );


        doc.setFont("helvetica", "normal");

        doc.setFontSize(10);

        doc.text(
            "1. Please complete payment before the due date.",
            20,
            finalY + 50
        );


        doc.text(
            "2. Keep the invoice number for future reference.",
            20,
            finalY + 58
        );


        doc.text(
            "3. Contact the billing administrator for payment issues.",
            20,
            finalY + 66
        );


        // ==============================
        // FOOTER
        // ==============================

        doc.setFontSize(10);

        doc.text(
            "Thank you for using Smart Water Billing System.",
            105,
            finalY + 85,
            {
                align: "center"
            }
        );


        // ==============================
        // SAVE PDF
        // ==============================

        const consumerNumber =
            invoice.consumerNumber || "consumer";

        const billingCycle =
            invoice.billingCycle
                ? invoice.billingCycle.replace(
                    /[^a-zA-Z0-9]/g,
                    "-"
                )
                : "invoice";


        doc.save(
            `Invoice-${consumerNumber}-${billingCycle}.pdf`
        );

    };


    // ==============================
    // SEARCH + FILTER
    // ==============================

    const filteredInvoices = invoices.filter(
        (invoice) => {

            const consumerNumber =
                invoice.consumerNumber || "";

            const matchesSearch =
                consumerNumber
                    .toLowerCase()
                    .includes(
                        search.toLowerCase()
                    );


            const matchesStatus =
                statusFilter === "ALL" ||
                invoice.status === statusFilter;


            return (
                matchesSearch &&
                matchesStatus
            );

        }
    );


    // ==============================
    // SUMMARY
    // ==============================

    const totalInvoices =
        invoices.length;


    const paidInvoices =
        invoices.filter(
            (invoice) =>
                invoice.status === "PAID"
        ).length;


    const unpaidInvoices =
        invoices.filter(
            (invoice) =>
                invoice.status === "UNPAID"
        ).length;


    const totalAmount =
        invoices.reduce(
            (sum, invoice) =>
                sum +
                Number(
                    invoice.totalAmount || 0
                ),
            0
        );


    // ==============================
    // UI
    // ==============================

    return (

        <Layout>

            {/* ==============================
                PAGE TITLE
            ============================== */}

            <h2 className="mb-4">
                Invoices
            </h2>


            {/* ==============================
                SUMMARY CARDS
            ============================== */}

            <div className="row mb-4">


                {/* TOTAL INVOICES */}

                <div className="col-md-3 mb-3">

                    <div className="card bg-primary text-white shadow">

                        <div className="card-body text-center">

                            <i className="bi bi-receipt display-5"></i>

                            <h5 className="mt-2">
                                Total Invoices
                            </h5>

                            <h2>
                                {totalInvoices}
                            </h2>

                        </div>

                    </div>

                </div>


                {/* PAID */}

                <div className="col-md-3 mb-3">

                    <div className="card bg-success text-white shadow">

                        <div className="card-body text-center">

                            <i className="bi bi-check-circle display-5"></i>

                            <h5 className="mt-2">
                                Paid
                            </h5>

                            <h2>
                                {paidInvoices}
                            </h2>

                        </div>

                    </div>

                </div>


                {/* UNPAID */}

                <div className="col-md-3 mb-3">

                    <div className="card bg-danger text-white shadow">

                        <div className="card-body text-center">

                            <i className="bi bi-exclamation-circle display-5"></i>

                            <h5 className="mt-2">
                                Unpaid
                            </h5>

                            <h2>
                                {unpaidInvoices}
                            </h2>

                        </div>

                    </div>

                </div>


                {/* TOTAL AMOUNT */}

                <div className="col-md-3 mb-3">

                    <div className="card bg-warning text-dark shadow">

                        <div className="card-body text-center">

                            <i className="bi bi-currency-rupee display-5"></i>

                            <h5 className="mt-2">
                                Total Amount
                            </h5>

                            <h2>
                                ₹ {totalAmount.toFixed(2)}
                            </h2>

                        </div>

                    </div>

                </div>

            </div>


            {/* ==============================
                SEARCH + FILTER
            ============================== */}

            <div className="card mb-4 shadow">

                <div className="card-body">

                    <div className="row">


                        {/* SEARCH */}

                        <div className="col-md-6">

                            <label className="form-label">

                                Search Consumer

                            </label>

                            <input
                                type="text"
                                className="form-control"
                                placeholder="Search Consumer Number"
                                value={search}
                                onChange={(e) =>
                                    setSearch(
                                        e.target.value
                                    )
                                }
                            />

                        </div>


                        {/* STATUS */}

                        <div className="col-md-3">

                            <label className="form-label">

                                Status

                            </label>

                            <select
                                className="form-select"
                                value={statusFilter}
                                onChange={(e) =>
                                    setStatusFilter(
                                        e.target.value
                                    )
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


                        {/* REFRESH */}

                        <div className="col-md-3 d-flex align-items-end">

                            <button
                                className="btn btn-primary w-100"
                                onClick={loadInvoices}
                            >

                                <i className="bi bi-arrow-clockwise me-2"></i>

                                Refresh

                            </button>

                        </div>

                    </div>

                </div>

            </div>


            {/* ==============================
                INVOICE TABLE
            ============================== */}

            <div className="card shadow">

                <div className="card-header bg-dark text-white">

                    <h5 className="mb-0">

                        Invoice Records

                    </h5>

                </div>


                <div className="card-body p-0">

                    {loading ? (

                        <div className="text-center p-5">

                            <div
                                className="spinner-border text-primary"
                                role="status"
                            >
                            </div>

                            <p className="mt-3">
                                Loading invoices...
                            </p>

                        </div>

                    ) : (

                        <div className="table-responsive">

                            <table className="table table-bordered table-striped mb-0">

                                <thead className="table-dark">

                                <tr>

                                    <th>Invoice ID</th>

                                    <th>Consumer</th>

                                    <th>Units</th>

                                    <th>Rate / Unit</th>

                                    <th>Total</th>

                                    <th>Invoice Date</th>

                                    <th>Billing Cycle</th>

                                    <th>Status</th>

                                    <th>Action</th>

                                </tr>

                                </thead>


                                <tbody>

                                {filteredInvoices.length === 0 ? (

                                    <tr>

                                        <td
                                            colSpan="9"
                                            className="text-center p-4"
                                        >

                                            No invoices found.

                                        </td>

                                    </tr>

                                ) : (

                                    filteredInvoices.map(
                                        (invoice, index) => {

                                            const invoiceId =
                                                invoice.id !== null &&
                                                invoice.id !== undefined
                                                    ? invoice.id
                                                    : invoice.waterBillId;


                                            return (

                                                <tr
                                                    key={
                                                        invoiceId ||
                                                        index
                                                    }
                                                >


                                                    {/* ID */}

                                                    <td>

                                                        INV-
                                                        {invoiceId}

                                                    </td>


                                                    {/* CONSUMER */}

                                                    <td>

                                                        {
                                                            invoice.consumerNumber ||
                                                            "-"
                                                        }

                                                    </td>


                                                    {/* UNITS */}

                                                    <td>

                                                        {
                                                            invoice.unitsConsumed ??
                                                            0
                                                        }

                                                    </td>


                                                    {/* RATE */}

                                                    <td>

                                                        ₹{" "}

                                                        {Number(
                                                            invoice.ratePerUnit ||
                                                            0
                                                        ).toFixed(2)}

                                                    </td>


                                                    {/* TOTAL */}

                                                    <td>

                                                        <strong>

                                                            ₹{" "}

                                                            {Number(
                                                                invoice.totalAmount ||
                                                                0
                                                            ).toFixed(2)}

                                                        </strong>

                                                    </td>


                                                    {/* DATE */}

                                                    <td>

                                                        {
                                                            invoice.billDate ||
                                                            "-"
                                                        }

                                                    </td>


                                                    {/* CYCLE */}

                                                    <td>

                                                        {
                                                            invoice.billingCycle ||
                                                            "-"
                                                        }

                                                    </td>


                                                    {/* STATUS */}

                                                    <td>

                                                        <span
                                                            className={
                                                                invoice.status ===
                                                                "PAID"
                                                                    ? "badge bg-success"
                                                                    : "badge bg-danger"
                                                            }
                                                        >

                                                            {
                                                                invoice.status ||
                                                                "UNPAID"
                                                            }

                                                        </span>

                                                    </td>


                                                    {/* ACTION */}

                                                    <td>

                                                        <button
                                                            className="btn btn-primary btn-sm me-2"
                                                            onClick={() =>
                                                                handleViewInvoice(
                                                                    invoice
                                                                )
                                                            }
                                                        >

                                                            <i className="bi bi-eye me-1"></i>

                                                            View

                                                        </button>


                                                        <button
                                                            className="btn btn-danger btn-sm"
                                                            onClick={() =>
                                                                downloadInvoice(
                                                                    invoice
                                                                )
                                                            }
                                                        >

                                                            <i className="bi bi-file-earmark-pdf me-1"></i>

                                                            PDF

                                                        </button>

                                                    </td>

                                                </tr>

                                            );

                                        }
                                    )

                                )}

                                </tbody>

                            </table>

                        </div>

                    )}

                </div>

            </div>


            {/* ==============================
                INVOICE DETAILS MODAL
            ============================== */}

            {selectedInvoice && (

                <div
                    className="modal d-block"
                    tabIndex="-1"
                    style={{
                        backgroundColor:
                            "rgba(0,0,0,0.5)"
                    }}
                >

                    <div className="modal-dialog modal-lg modal-dialog-centered">

                        <div className="modal-content">


                            {/* MODAL HEADER */}

                            <div className="modal-header bg-primary text-white">

                                <h5 className="modal-title">

                                    Invoice Details

                                </h5>

                                <button
                                    type="button"
                                    className="btn-close btn-close-white"
                                    onClick={
                                        closeInvoiceDetails
                                    }
                                >
                                </button>

                            </div>


                            {/* MODAL BODY */}

                            <div className="modal-body">

                                <div className="row mb-3">


                                    <div className="col-md-6">

                                        <strong>
                                            Consumer Number:
                                        </strong>

                                        <p>
                                            {
                                                selectedInvoice.consumerNumber ||
                                                "-"
                                            }
                                        </p>

                                    </div>


                                    <div className="col-md-6">

                                        <strong>
                                            Billing Cycle:
                                        </strong>

                                        <p>
                                            {
                                                selectedInvoice.billingCycle ||
                                                "-"
                                            }
                                        </p>

                                    </div>


                                    <div className="col-md-6">

                                        <strong>
                                            Invoice Date:
                                        </strong>

                                        <p>
                                            {
                                                selectedInvoice.billDate ||
                                                "-"
                                            }
                                        </p>

                                    </div>


                                    <div className="col-md-6">

                                        <strong>
                                            Status:
                                        </strong>

                                        <p>

                                            <span
                                                className={
                                                    selectedInvoice.status ===
                                                    "PAID"
                                                        ? "badge bg-success"
                                                        : "badge bg-danger"
                                                }
                                            >

                                                {
                                                    selectedInvoice.status ||
                                                    "UNPAID"
                                                }

                                            </span>

                                        </p>

                                    </div>

                                </div>


                                {/* CONSUMPTION DETAILS */}

                                <table className="table table-bordered">

                                    <tbody>

                                    <tr>

                                        <th>
                                            Water Consumption
                                        </th>

                                        <td>
                                            {
                                                selectedInvoice.unitsConsumed ||
                                                0
                                            }{" "}
                                            units
                                        </td>

                                    </tr>


                                    <tr>

                                        <th>
                                            Rate Per Unit
                                        </th>

                                        <td>
                                            ₹{" "}
                                            {Number(
                                                selectedInvoice.ratePerUnit ||
                                                0
                                            ).toFixed(2)}
                                        </td>

                                    </tr>


                                    <tr>

                                        <th>
                                            Total Amount
                                        </th>

                                        <td>

                                            <strong>

                                                ₹{" "}
                                                {Number(
                                                    selectedInvoice.totalAmount ||
                                                    0
                                                ).toFixed(2)}

                                            </strong>

                                        </td>

                                    </tr>

                                    </tbody>

                                </table>


                                {/* PAYMENT INSTRUCTIONS */}

                                <div className="alert alert-info">

                                    <strong>
                                        Payment Instructions
                                    </strong>

                                    <ul className="mb-0 mt-2">

                                        <li>
                                            Please complete payment before the due date.
                                        </li>

                                        <li>
                                            Keep your invoice number for future reference.
                                        </li>

                                        <li>
                                            Contact the billing administrator for payment issues.
                                        </li>

                                    </ul>

                                </div>

                            </div>


                            {/* MODAL FOOTER */}

                            <div className="modal-footer">

                                <button
                                    className="btn btn-secondary"
                                    onClick={
                                        closeInvoiceDetails
                                    }
                                >

                                    Close

                                </button>


                                <button
                                    className="btn btn-danger"
                                    onClick={() =>
                                        downloadInvoice(
                                            selectedInvoice
                                        )
                                    }
                                >

                                    <i className="bi bi-file-earmark-pdf me-2"></i>

                                    Download PDF

                                </button>

                            </div>

                        </div>

                    </div>

                </div>

            )}

        </Layout>

    );

}

export default Invoices;