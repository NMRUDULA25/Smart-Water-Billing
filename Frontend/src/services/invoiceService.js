import api from "./api";

export const getAllInvoices = () => {
    return api.get("/invoices");
};

export const getInvoiceById = (id) => {
    return api.get(`/invoices/${id}`);
};

export const getInvoiceByWaterBillId = (waterBillId) => {
    return api.get(`/invoices/water-bill/${waterBillId}`);
};

export const generateInvoice = (waterBillId) => {
    return api.post(`/invoices/generate/${waterBillId}`);
};

export const downloadInvoice = (invoiceId) => {
    return api.get(`/invoices/${invoiceId}/pdf`, {
        responseType: "blob"
    });
};