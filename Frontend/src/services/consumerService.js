import api from "./api";

export const getAllConsumers = () => {
    return api.get("/consumers");
};

export const getConsumerById = (id) => {
    return api.get(`/consumers/${id}`);
};

export const createConsumer = (consumer) => {
    return api.post("/consumers", consumer);
};

export const updateConsumer = (id, consumer) => {
    return api.put(`/consumers/${id}`, consumer);
};

export const deleteConsumer = (id) => {
    return api.delete(`/consumers/${id}`);
};