import api from "./api";

export const getAllMeterReadings = () => {
    return api.get("/meter-readings");
};

export const createMeterReading = (reading) => {
    return api.post("/meter-readings", reading);
};

export const deleteMeterReading = (id) => {
    return api.delete(`/meter-readings/${id}`);
};