import api from "./client";

export const getAllTransactions = async () => {
	const response = await api.get("/transactions");
	return response.data;
};

export const issueBook = async (transactionData) => {
	const response = await api.post("/transactions/issue", transactionData);

	return response.data;
};

export const returnBook = async (transactionData) => {
	const response = await api.post("/transactions/return", transactionData);

	return response.data;
};
