import api from "./api";

export const getAllPayments = () => {
    return api.get("/payments");
};

export const createPayment = (payment) => {
    return api.post("/payments", payment);
};

export const updatePayment = (id, payment) => {
    return api.put(`/payments/${id}`, payment);
};

export const deletePayment = (id) => {
    return api.delete(`/payments/${id}`);
};

export const getPaymentById = (id) => {
    return api.get(`/payments/${id}`);
};