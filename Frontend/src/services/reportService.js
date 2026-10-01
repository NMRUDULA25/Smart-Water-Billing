import api from "./api";

export const getConsumers = () => {
    return api.get("/consumers");
};

export const getMeterReadings = () => {
    return api.get("/meter-readings");
};

export const getBills = () => {
    return api.get("/water-bills");
};
export const getDashboardReport = () => {
    return api.get("/reports/dashboard");
};