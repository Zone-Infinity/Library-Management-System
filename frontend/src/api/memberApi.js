import client from "./client";

export const getAllMembers = async () => {
	const response = await client.get("/members");

	return response.data || [];
};

export const addMember = async (member) => {
	const response = await client.post("/members", member);

	return response.data;
};

export const updateMember = async (member) => {
	const response = await client.put("/members", member);

	return response.data;
};

export const deleteMember = async (id) => {
	await client.delete(`/members/${id}`);

	return true;
};

export const getMemberTransactions = async (id) => {
	const response = await client.get(`/members/${id}/transactions`);

	return response.data || [];
};