import axios from "axios";

const client = axios.create({
	baseURL: "http://localhost:8080/api",
	headers: {
		"Content-Type": "application/json",
	},
});

client.interceptors.response.use(
	(response) => response,

	(error) => {
		const response = error.response;

		if (!response) {
			return Promise.reject({
				message: "Server unreachable",
			});
		}

		const data = response.data;

		// validation errors
		if (data && typeof data === "object") {
			if (data.errors) {
				const messages = Object.values(data.errors).join(", ");

				return Promise.reject({
					message: messages,
				});
			}

			if (data.message) {
				return Promise.reject({
					message: data.message,
				});
			}
		}

		return Promise.reject({
			message: "Something went wrong",
		});
	},
);

export default client;
