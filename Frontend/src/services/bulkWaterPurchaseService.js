import axios from "axios";

const API_URL = "http://localhost:8080/api/bulk-water-purchases";

// GET ALL
export const getAllBulkWaterPurchases = () => {
    return axios.get(API_URL);
};

// GET BY ID
export const getBulkWaterPurchaseById = (id) => {
    return axios.get(`${API_URL}/${id}`);
};

// CREATE
export const createBulkWaterPurchase = (purchase) => {
    return axios.post(API_URL, purchase);
};

// UPDATE
export const updateBulkWaterPurchase = (id, purchase) => {
    return axios.put(`${API_URL}/${id}`, purchase);
};

// DELETE
export const deleteBulkWaterPurchase = (id) => {
    return axios.delete(`${API_URL}/${id}`);
};