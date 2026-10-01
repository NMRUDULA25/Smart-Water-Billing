import api from "./api";

export const getAllBills = () => {
    return api.get("/water-bills");
};

export const createBill = (bill) => {
    return api.post("/water-bills", bill);
};

export const updateBill = (id, bill) => {
    return api.put(`/water-bills/${id}`, bill);
};

export const deleteBill = (id) => {
    return api.delete(`/water-bills/${id}`);
};

export const markBillPaid = (id) => {
    return api.put(`/water-bills/${id}/pay`);
};