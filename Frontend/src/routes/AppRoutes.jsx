import {
    BrowserRouter,
    Routes,
    Route,
    Navigate
} from "react-router-dom";

import Dashboard from "../pages/Dashboard";
import Consumers from "../pages/Consumers";
import MeterReadings from "../pages/MeterReadings";
import Bills from "../pages/Bills";
import Payments from "../pages/Payments";
import TariffPlans from "../pages/TariffPlans";
import BillingCycles from "../pages/BillingCycles";
import BulkWaterPurchases from "../pages/BulkWaterPurchases";
import Invoices from "../pages/Invoices";
import ConsumptionComparison from "../pages/ConsumptionComparison";
import Reports from "../pages/Reports";

function AppRoutes() {

    return (
        <BrowserRouter>

            <Routes>

                {/* Default */}
                <Route
                    path="/"
                    element={<Navigate to="/dashboard" replace />}
                />

                {/* Dashboard */}
                <Route
                    path="/dashboard"
                    element={<Dashboard />}
                />

                {/* Consumers */}
                <Route
                    path="/consumers"
                    element={<Consumers />}
                />

                {/* Meter Readings */}
                <Route
                    path="/meter-readings"
                    element={<MeterReadings />}
                />

                {/* Bills */}
                <Route
                    path="/bills"
                    element={<Bills />}
                />

                {/* Payments */}
                <Route
                    path="/payments"
                    element={<Payments />}
                />

                {/* Tariff Plans */}
                <Route
                    path="/tariff-plans"
                    element={<TariffPlans />}
                />

                {/* Billing Cycles */}
                <Route
                    path="/billing-cycles"
                    element={<BillingCycles />}
                />

                {/* Bulk Water Purchases */}
                <Route
                    path="/bulk-water-purchases"
                    element={<BulkWaterPurchases />}
                />

                {/* Invoices */}
                <Route
                    path="/invoices"
                    element={<Invoices />}
                />

                {/* Consumption Comparison */}
                <Route
                    path="/consumption-comparison"
                    element={<ConsumptionComparison />}
                />

                {/* Reports */}
                <Route
                    path="/reports"
                    element={<Reports />}
                />

            </Routes>

        </BrowserRouter>
    );
}

export default AppRoutes;