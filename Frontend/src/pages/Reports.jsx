import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import { getDashboardReport } from "../services/reportService";

import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    ArcElement,
    Title,
    Tooltip,
    Legend
} from "chart.js";

import { Bar, Pie } from "react-chartjs-2";

// Register Chart.js components
ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    ArcElement,
    Title,
    Tooltip,
    Legend
);

function Reports() {

    // ==============================
    // REPORT DATA
    // ==============================

    const [report, setReport] = useState({
        totalConsumers: 0,
        totalMeterReadings: 0,
        totalBills: 0,
        paidBills: 0,
        unpaidBills: 0,
        totalPayments: 0,
        totalRevenue: 0
    });

    const [loading, setLoading] = useState(true);

    // ==============================
    // LOAD REPORT
    // ==============================

    useEffect(() => {
        loadReport();
    }, []);

    const loadReport = async () => {

        try {

            setLoading(true);

            const response = await getDashboardReport();

            setReport({
                totalConsumers: response.data.totalConsumers || 0,
                totalMeterReadings: response.data.totalMeterReadings || 0,
                totalBills: response.data.totalBills || 0,
                paidBills: response.data.paidBills || 0,
                unpaidBills: response.data.unpaidBills || 0,
                totalPayments: response.data.totalPayments || 0,
                totalRevenue: response.data.totalRevenue || 0
            });

        } catch (error) {

            console.error("Error loading report:", error);

            alert("Unable to load report data.");

        } finally {

            setLoading(false);

        }

    };

    // ==============================
    // DOWNLOAD PDF
    // ==============================

    const downloadPDF = () => {

        const doc = new jsPDF();

        // Title
        doc.setFontSize(18);
        doc.text(
            "SMART WATER BILLING SYSTEM",
            20,
            20
        );

        // Subtitle
        doc.setFontSize(14);
        doc.text(
            "Dashboard Report",
            20,
            30
        );

        // Report table
        autoTable(doc, {

            startY: 40,

            head: [
                ["Report", "Value"]
            ],

            body: [

                [
                    "Total Consumers",
                    report.totalConsumers
                ],

                [
                    "Total Meter Readings",
                    report.totalMeterReadings
                ],

                [
                    "Total Bills",
                    report.totalBills
                ],

                [
                    "Paid Bills",
                    report.paidBills
                ],

                [
                    "Unpaid Bills",
                    report.unpaidBills
                ],

                [
                    "Total Payments",
                    report.totalPayments
                ],

                [
                    "Total Revenue",
                    "Rs. " +
                    Number(report.totalRevenue).toFixed(2)
                ]

            ]

        });

        const generatedDate =
            new Date().toLocaleString();

        doc.text(
            "Generated On : " + generatedDate,
            20,
            doc.lastAutoTable.finalY + 15
        );

        doc.save(
            "SmartWaterBillingReport.pdf"
        );

    };

    // ==============================
    // BAR CHART DATA
    // ==============================

    const barData = {

        labels: [
            "Consumers",
            "Meter Readings",
            "Bills",
            "Payments"
        ],

        datasets: [

            {

                label: "System Statistics",

                data: [

                    report.totalConsumers,
                    report.totalMeterReadings,
                    report.totalBills,
                    report.totalPayments

                ],

                backgroundColor: [

                    "rgba(13, 110, 253, 0.85)",
                    "rgba(25, 135, 84, 0.85)",
                    "rgba(255, 193, 7, 0.85)",
                    "rgba(220, 53, 69, 0.85)"

                ],

                borderColor: [

                    "rgb(13, 110, 253)",
                    "rgb(25, 135, 84)",
                    "rgb(255, 193, 7)",
                    "rgb(220, 53, 69)"

                ],

                borderWidth: 2,

                borderRadius: 6

            }

        ]

    };

    // ==============================
    // PIE CHART DATA
    // ==============================

    const pieData = {

        labels: [
            "Paid Bills",
            "Unpaid Bills"
        ],

        datasets: [

            {

                data: [

                    report.paidBills,
                    report.unpaidBills

                ],

                backgroundColor: [

                    "rgba(25, 135, 84, 0.85)",
                    "rgba(220, 53, 69, 0.85)"

                ],

                borderColor: [

                    "rgb(25, 135, 84)",
                    "rgb(220, 53, 69)"

                ],

                borderWidth: 2

            }

        ]

    };

    // ==============================
    // BAR CHART OPTIONS
    // ==============================

    const barOptions = {

        responsive: true,

        maintainAspectRatio: false,

        plugins: {

            legend: {
                display: true,
                position: "top"
            },

            title: {
                display: false
            },

            tooltip: {
                enabled: true
            }

        },

        scales: {

            y: {

                beginAtZero: true,

                ticks: {
                    precision: 0
                }

            }

        }

    };

    // ==============================
    // PIE CHART OPTIONS
    // ==============================

    const pieOptions = {

        responsive: true,

        maintainAspectRatio: false,

        plugins: {

            legend: {

                display: true,

                position: "bottom"

            },

            tooltip: {

                enabled: true

            }

        }

    };

    // ==============================
    // UI
    // ==============================

    return (

        <Layout>

            {/* PAGE TITLE */}

            <div className="d-flex justify-content-between align-items-center mb-4">

                <div>

                    <h2 className="mb-1">
                        Reports Dashboard
                    </h2>

                    <p className="text-muted mb-0">
                        Smart Water Billing System Statistics
                    </p>

                </div>

                <button
                    className="btn btn-danger"
                    onClick={downloadPDF}
                    disabled={loading}
                >

                    <i className="bi bi-file-earmark-pdf me-2"></i>

                    Download PDF

                </button>

            </div>


            {/* ==============================
                SUMMARY CARDS
            ============================== */}

            {loading ? (

                <div className="text-center my-5">

                    <div
                        className="spinner-border text-primary"
                        role="status"
                    >
                    </div>

                    <p className="mt-2">
                        Loading report...
                    </p>

                </div>

            ) : (

                <>

                    <div className="row">

                        {/* CONSUMERS */}

                        <div className="col-md-3 mb-3">

                            <div className="card bg-primary text-white shadow h-100">

                                <div className="card-body text-center">

                                    <i className="bi bi-people-fill display-5"></i>

                                    <h5 className="mt-3">
                                        Consumers
                                    </h5>

                                    <h2>
                                        {report.totalConsumers}
                                    </h2>

                                </div>

                            </div>

                        </div>


                        {/* METER READINGS */}

                        <div className="col-md-3 mb-3">

                            <div className="card bg-success text-white shadow h-100">

                                <div className="card-body text-center">

                                    <i className="bi bi-droplet-fill display-5"></i>

                                    <h5 className="mt-3">
                                        Meter Readings
                                    </h5>

                                    <h2>
                                        {report.totalMeterReadings}
                                    </h2>

                                </div>

                            </div>

                        </div>


                        {/* BILLS */}

                        <div className="col-md-3 mb-3">

                            <div className="card bg-warning text-dark shadow h-100">

                                <div className="card-body text-center">

                                    <i className="bi bi-receipt-cutoff display-5"></i>

                                    <h5 className="mt-3">
                                        Total Bills
                                    </h5>

                                    <h2>
                                        {report.totalBills}
                                    </h2>

                                </div>

                            </div>

                        </div>


                        {/* REVENUE */}

                        <div className="col-md-3 mb-3">

                            <div className="card bg-dark text-white shadow h-100">

                                <div className="card-body text-center">

                                    <i className="bi bi-currency-rupee display-5"></i>

                                    <h5 className="mt-3">
                                        Revenue
                                    </h5>

                                    <h2>
                                        ₹ {Number(report.totalRevenue).toFixed(2)}
                                    </h2>

                                </div>

                            </div>

                        </div>

                    </div>


                    {/* ==============================
                        SECONDARY CARDS
                    ============================== */}

                    <div className="row mt-2">

                        {/* PAID */}

                        <div className="col-md-4 mb-3">

                            <div className="card bg-success text-white shadow">

                                <div className="card-body text-center">

                                    <i className="bi bi-check-circle-fill display-6"></i>

                                    <h5 className="mt-2">
                                        Paid Bills
                                    </h5>

                                    <h2>
                                        {report.paidBills}
                                    </h2>

                                </div>

                            </div>

                        </div>


                        {/* UNPAID */}

                        <div className="col-md-4 mb-3">

                            <div className="card bg-danger text-white shadow">

                                <div className="card-body text-center">

                                    <i className="bi bi-exclamation-circle-fill display-6"></i>

                                    <h5 className="mt-2">
                                        Unpaid Bills
                                    </h5>

                                    <h2>
                                        {report.unpaidBills}
                                    </h2>

                                </div>

                            </div>

                        </div>


                        {/* PAYMENTS */}

                        <div className="col-md-4 mb-3">

                            <div className="card bg-info text-white shadow">

                                <div className="card-body text-center">

                                    <i className="bi bi-credit-card-fill display-6"></i>

                                    <h5 className="mt-2">
                                        Total Payments
                                    </h5>

                                    <h2>
                                        {report.totalPayments}
                                    </h2>

                                </div>

                            </div>

                        </div>

                    </div>


                    {/* ==============================
                        CHARTS
                    ============================== */}

                    <div className="row mt-4">


                        {/* BAR CHART */}

                        <div className="col-md-7 mb-4">

                            <div className="card shadow h-100">

                                <div className="card-header bg-primary text-white">

                                    <h5 className="mb-0">

                                        <i className="bi bi-bar-chart-fill me-2"></i>

                                        System Statistics

                                    </h5>

                                </div>

                                <div
                                    className="card-body"
                                    style={{
                                        height: "400px"
                                    }}
                                >

                                    <Bar
                                        data={barData}
                                        options={barOptions}
                                    />

                                </div>

                            </div>

                        </div>


                        {/* PIE CHART */}

                        <div className="col-md-5 mb-4">

                            <div className="card shadow h-100">

                                <div className="card-header bg-success text-white">

                                    <h5 className="mb-0">

                                        <i className="bi bi-pie-chart-fill me-2"></i>

                                        Bill Status

                                    </h5>

                                </div>

                                <div
                                    className="card-body"
                                    style={{
                                        height: "400px"
                                    }}
                                >

                                    <Pie
                                        data={pieData}
                                        options={pieOptions}
                                    />

                                </div>

                            </div>

                        </div>

                    </div>


                    {/* ==============================
                        REPORT INFORMATION
                    ============================== */}

                    <div className="card shadow mb-4">

                        <div className="card-header bg-secondary text-white">

                            <h5 className="mb-0">

                                <i className="bi bi-info-circle me-2"></i>

                                Report Information

                            </h5>

                        </div>

                        <div className="card-body">

                            <div className="row">

                                <div className="col-md-6">

                                    <p>
                                        <strong>
                                            Project:
                                        </strong>{" "}
                                        Smart Water Billing System
                                    </p>

                                    <p>
                                        <strong>
                                            Backend:
                                        </strong>{" "}
                                        Spring Boot
                                    </p>

                                    <p>
                                        <strong>
                                            Frontend:
                                        </strong>{" "}
                                        React.js
                                    </p>

                                </div>

                                <div className="col-md-6">

                                    <p>
                                        <strong>
                                            Database:
                                        </strong>{" "}
                                        PostgreSQL
                                    </p>

                                    <p>
                                        <strong>
                                            Report Type:
                                        </strong>{" "}
                                        System Dashboard Report
                                    </p>

                                    <p>
                                        <strong>
                                            Status:
                                        </strong>{" "}
                                        <span className="badge bg-success">
                                            Active
                                        </span>
                                    </p>

                                </div>

                            </div>

                        </div>

                    </div>

                </>

            )}

        </Layout>

    );

}

export default Reports;