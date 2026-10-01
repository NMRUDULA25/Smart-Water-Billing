import {
    Chart as ChartJS,
    ArcElement,
    Tooltip,
    Legend,
    CategoryScale,
    LinearScale,
    BarElement
} from "chart.js";

import { Pie, Bar } from "react-chartjs-2";

ChartJS.register(
    ArcElement,
    Tooltip,
    Legend,
    CategoryScale,
    LinearScale,
    BarElement
);

function DashboardCharts({
                             paidBills,
                             unpaidBills,
                             revenue
                         }) {

    const pieData = {

        labels: ["Paid Bills", "Unpaid Bills"],

        datasets: [

            {

                data: [paidBills, unpaidBills],

                backgroundColor: [
                    "#28a745",
                    "#dc3545"
                ]

            }

        ]

    };

    const barData = {

        labels: ["Revenue"],

        datasets: [

            {

                label: "Revenue",

                data: [revenue]

            }

        ]

    };

    return (

        <div className="row mt-5">

            <div className="col-md-6">

                <div className="card">

                    <div className="card-header">

                        Bills Overview

                    </div>

                    <div className="card-body">

                        <Pie data={pieData} />

                    </div>

                </div>

            </div>

            <div className="col-md-6">

                <div className="card">

                    <div className="card-header">

                        Revenue

                    </div>

                    <div className="card-body">

                        <Bar data={barData} />

                    </div>

                </div>

            </div>

        </div>

    );

}

export default DashboardCharts;