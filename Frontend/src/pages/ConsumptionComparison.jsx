import { useEffect, useMemo, useState } from "react";
import Layout from "../components/Layout";
import { getAllMeterReadings } from "../services/meterReadingService";
import {
    Bar,
    Line
} from "react-chartjs-2";

import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    LineElement,
    PointElement,
    Title,
    Tooltip,
    Legend
} from "chart.js";

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    LineElement,
    PointElement,
    Title,
    Tooltip,
    Legend
);

function ConsumptionComparison() {

    const [readings, setReadings] = useState([]);
    const [selectedConsumer, setSelectedConsumer] = useState("");
    const [loading, setLoading] = useState(true);

    // ==============================
    // LOAD METER READINGS
    // ==============================

    useEffect(() => {
        loadReadings();
    }, []);

    const loadReadings = async () => {

        try {

            setLoading(true);

            const response = await getAllMeterReadings();

            const data = Array.isArray(response.data)
                ? response.data
                : [];

            setReadings(data);

            // Select first consumer automatically
            if (data.length > 0) {

                const consumers = [
                    ...new Set(
                        data
                            .map((reading) => reading.consumerNumber)
                            .filter(Boolean)
                    )
                ];

                if (consumers.length > 0) {
                    setSelectedConsumer(consumers[0]);
                }
            }

        } catch (error) {

            console.error(
                "Error loading consumption data:",
                error
            );

            alert("Unable to load consumption data.");

        } finally {

            setLoading(false);

        }

    };

    // ==============================
    // UNIQUE CONSUMERS
    // ==============================

    const consumers = useMemo(() => {

        return [
            ...new Set(
                readings
                    .map((reading) => reading.consumerNumber)
                    .filter(Boolean)
            )
        ];

    }, [readings]);

    // ==============================
    // SELECTED CONSUMER READINGS
    // ==============================

    const consumerReadings = useMemo(() => {

        return readings
            .filter(
                (reading) =>
                    reading.consumerNumber === selectedConsumer
            )
            .sort(
                (a, b) =>
                    new Date(a.readingDate) -
                    new Date(b.readingDate)
            );

    }, [readings, selectedConsumer]);

    // ==============================
    // TOTAL CONSUMPTION
    // ==============================

    const yourUsage = useMemo(() => {

        return consumerReadings.reduce(
            (sum, reading) =>
                sum + Number(reading.unitsConsumed || 0),
            0
        );

    }, [consumerReadings]);

    // ==============================
    // APARTMENT AVERAGE
    // ==============================

    const apartmentAverage = useMemo(() => {

        if (consumers.length === 0) {
            return 0;
        }

        const consumerTotals = consumers.map(
            (consumer) => {

                return readings
                    .filter(
                        (reading) =>
                            reading.consumerNumber === consumer
                    )
                    .reduce(
                        (sum, reading) =>
                            sum +
                            Number(
                                reading.unitsConsumed || 0
                            ),
                        0
                    );

            }
        );

        const total = consumerTotals.reduce(
            (sum, value) => sum + value,
            0
        );

        return total / consumerTotals.length;

    }, [readings, consumers]);

    // ==============================
    // DIFFERENCE
    // ==============================

    const difference = yourUsage - apartmentAverage;

    const differencePercentage =
        apartmentAverage > 0
            ? (difference / apartmentAverage) * 100
            : 0;

    // ==============================
    // USAGE STATUS
    // ==============================

    let usageStatus = "Average";
    let statusClass = "text-primary";

    if (differencePercentage > 10) {

        usageStatus = "Above Average";
        statusClass = "text-danger";

    } else if (differencePercentage < -10) {

        usageStatus = "Below Average";
        statusClass = "text-success";

    }

    // ==============================
    // BAR CHART
    // ==============================

    const comparisonData = {

        labels: [
            "Your Household",
            "Apartment Average"
        ],

        datasets: [
            {
                label: "Water Consumption (KL)",

                data: [
                    yourUsage,
                    apartmentAverage
                ]
            }
        ]

    };

    const comparisonOptions = {

        responsive: true,

        plugins: {

            legend: {
                display: true
            },

            title: {
                display: true,
                text: "Household vs Apartment Average"
            }

        },

        scales: {

            y: {

                beginAtZero: true,

                title: {
                    display: true,
                    text: "Consumption (KL)"
                }

            }

        }

    };

    // ==============================
    // MONTHLY / READING TREND
    // ==============================

    const trendData = {

        labels: consumerReadings.map(
            (reading) => reading.readingDate
        ),

        datasets: [
            {
                label: "Your Consumption",

                data: consumerReadings.map(
                    (reading) =>
                        Number(
                            reading.unitsConsumed || 0
                        )
                ),

                tension: 0.3
            }
        ]

    };

    const trendOptions = {

        responsive: true,

        plugins: {

            legend: {
                display: true
            },

            title: {
                display: true,
                text: "Water Consumption Trend"
            }

        },

        scales: {

            y: {

                beginAtZero: true,

                title: {
                    display: true,
                    text: "Units Consumed (KL)"
                }

            }

        }

    };

    // ==============================
    // LOADING
    // ==============================

    if (loading) {

        return (

            <Layout>

                <div className="text-center mt-5">

                    <div
                        className="spinner-border text-primary"
                        role="status"
                    ></div>

                    <p className="mt-3">
                        Loading consumption data...
                    </p>

                </div>

            </Layout>

        );

    }

    // ==============================
    // UI
    // ==============================

    return (

        <Layout>

            <h2 className="mb-4">
                Consumption Comparison
            </h2>

            <p className="text-muted">
                Compare household water consumption with
                the apartment average.
            </p>


            {/* ==============================
                CONSUMER SELECTOR
            ============================== */}

            <div className="card mb-4 shadow-sm">

                <div className="card-body">

                    <div className="row align-items-center">

                        <div className="col-md-4">

                            <label className="form-label">
                                Select Consumer
                            </label>

                            <select
                                className="form-select"
                                value={selectedConsumer}
                                onChange={(e) =>
                                    setSelectedConsumer(
                                        e.target.value
                                    )
                                }
                            >

                                <option value="">
                                    Select Consumer
                                </option>

                                {consumers.map(
                                    (consumer) => (

                                        <option
                                            key={consumer}
                                            value={consumer}
                                        >
                                            {consumer}
                                        </option>

                                    )
                                )}

                            </select>

                        </div>

                    </div>

                </div>

            </div>


            {/* ==============================
                SUMMARY CARDS
            ============================== */}

            <div className="row mb-4">

                <div className="col-md-4 mb-3">

                    <div className="card bg-primary text-white shadow">

                        <div className="card-body text-center">

                            <i className="bi bi-droplet-fill display-5"></i>

                            <h5 className="mt-2">
                                Your Usage
                            </h5>

                            <h2>
                                {yourUsage.toFixed(2)} KL
                            </h2>

                        </div>

                    </div>

                </div>


                <div className="col-md-4 mb-3">

                    <div className="card bg-info text-white shadow">

                        <div className="card-body text-center">

                            <i className="bi bi-building display-5"></i>

                            <h5 className="mt-2">
                                Apartment Average
                            </h5>

                            <h2>
                                {apartmentAverage.toFixed(2)} KL
                            </h2>

                        </div>

                    </div>

                </div>


                <div className="col-md-4 mb-3">

                    <div className="card bg-light shadow">

                        <div className="card-body text-center">

                            <h5>
                                Comparison
                            </h5>

                            <h2 className={statusClass}>
                                {differencePercentage >= 0
                                    ? "+"
                                    : ""}
                                {differencePercentage.toFixed(1)}%
                            </h2>

                            <p className={`fw-bold ${statusClass}`}>
                                {usageStatus}
                            </p>

                        </div>

                    </div>

                </div>

            </div>


            {/* ==============================
                COMPARISON CHART
            ============================== */}

            <div className="card shadow mb-4">

                <div className="card-header bg-primary text-white">

                    <h5 className="mb-0">
                        Consumption Comparison
                    </h5>

                </div>

                <div className="card-body">

                    {consumers.length > 0 ? (

                        <Bar
                            data={comparisonData}
                            options={comparisonOptions}
                        />

                    ) : (

                        <p className="text-center">
                            No consumption data available.
                        </p>

                    )}

                </div>

            </div>


            {/* ==============================
                TREND CHART
            ============================== */}

            <div className="card shadow mb-4">

                <div className="card-header bg-success text-white">

                    <h5 className="mb-0">
                        Consumption Trend
                    </h5>

                </div>

                <div className="card-body">

                    {consumerReadings.length > 0 ? (

                        <Line
                            data={trendData}
                            options={trendOptions}
                        />

                    ) : (

                        <p className="text-center">
                            No readings available for this consumer.
                        </p>

                    )}

                </div>

            </div>


            {/* ==============================
                SIMILAR HOUSEHOLDS
            ============================== */}

            <div className="card shadow mb-4">

                <div className="card-header bg-secondary text-white">

                    <h5 className="mb-0">
                        Similar Household Comparison
                    </h5>

                </div>

                <div className="card-body">

                    <div className="alert alert-info">

                        <i className="bi bi-info-circle me-2"></i>

                        Similar-household comparison requires
                        household-size information and multiple
                        comparable households.

                    </div>

                    <p className="mb-0">

                        This section is ready for integration
                        once household-size data is available
                        in the backend.

                    </p>

                </div>

            </div>


            {/* ==============================
                WATER SAVING TIPS
            ============================== */}

            <div className="card shadow mb-4">

                <div className="card-header bg-success text-white">

                    <h5 className="mb-0">
                        <i className="bi bi-lightbulb me-2"></i>
                        Water Saving Tips
                    </h5>

                </div>

                <div className="card-body">

                    <div className="row">

                        <div className="col-md-6">

                            <ul className="list-group">

                                <li className="list-group-item">
                                    💧 Fix leaking taps and pipes
                                    immediately.
                                </li>

                                <li className="list-group-item">
                                    🚿 Avoid unnecessarily long
                                    showers.
                                </li>

                                <li className="list-group-item">
                                    🪣 Use only the required amount
                                    of water for cleaning.
                                </li>

                            </ul>

                        </div>

                        <div className="col-md-6">

                            <ul className="list-group">

                                <li className="list-group-item">
                                    🌱 Reuse suitable household
                                    water for plants.
                                </li>

                                <li className="list-group-item">
                                    🔍 Monitor your meter readings
                                    regularly.
                                </li>

                                <li className="list-group-item">
                                    📉 Try to keep consumption near
                                    or below the apartment average.
                                </li>

                            </ul>

                        </div>

                    </div>

                </div>

            </div>


            {/* ==============================
                NO DATA
            ============================== */}

            {readings.length === 0 && (

                <div className="alert alert-warning">

                    No meter readings are available yet.
                    Add meter readings from the
                    <strong> Meter Readings </strong>
                    page first.

                </div>

            )}

        </Layout>

    );

}

export default ConsumptionComparison;