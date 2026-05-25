import client from "./client";

export const getAllBooks = async () => {
	const response = await client.get("/books");
	return response.data;
};

export const getBookById = async (id) => {
	const response = await client.get(`/books/${id}`);
	return response.data;
};

export const saveBook = async (bookData) => {
	const response = await client.post("/books", bookData);

	return response.data;
};

// export const updateBook = async (bookData) => {
// 	const response = await client.put("/books", bookData);
// 	return response.data;
// };

export const addBookQuantity = async (id, quantity) => {
	const response = await client.patch("/books/add", {
		id,
		quantity,
	});

	return response.data;
};

export const removeBookQuantity = async (id, quantity) => {
	const response = await client.patch("/books/remove", {
		id,
		quantity,
	});

	return response.data;
};

export const deleteBook = async (id) => {
	await client.delete(`/books/${id}`);
	return true;
};
