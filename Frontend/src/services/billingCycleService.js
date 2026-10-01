import api from "./api";

export const getAllBillingCycles = () => {
    return api.get("/billing-cycles");
};

export const createBillingCycle = (cycle) => {
    return api.post("/billing-cycles", cycle);
};

export const updateBillingCycle = (id, cycle) => {
    return api.put(`/billing-cycles/${id}`, cycle);
};

export const deleteBillingCycle = (id) => {
    return api.delete(`/billing-cycles/${id}`);
};

export const openBillingCycle = (id) => {
    return api.put(`/billing-cycles/${id}/open`);
};

export const finalizeBillingCycle = (id) => {
    return api.put(`/billing-cycles/${id}/finalize`);
};

export const archiveBillingCycle = (id) => {
    return api.put(`/billing-cycles/${id}/archive`);
};