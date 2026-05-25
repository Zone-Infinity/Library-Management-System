import { useState } from "react";

import Modal from "../common/Modal";

import { saveBook } from "../../api/bookApi";

function BookModal({ onClose, onSuccess }) {
	const [formData, setFormData] = useState({
		title: "",
		author: "",
		quantity: 1,
	});

	const [loading, setLoading] = useState(false);

	const [error, setError] = useState("");

	const handleChange = (e) => {
		setFormData({
			...formData,
			[e.target.name]:
				e.target.name === "quantity" ? Number(e.target.value) : e.target.value,
		});
	};

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			setLoading(true);
			setError("");

			await saveBook(formData);

			onSuccess();
			onClose();
		} catch (err) {
			setError(err.message || "Failed to save book");
		} finally {
			setLoading(false);
		}
	};

	return (
		<Modal title="Add Book" onClose={onClose}>
			<form onSubmit={handleSubmit} className="space-y-4">
				<input
					type="text"
					name="title"
					placeholder="Title"
					value={formData.title}
					onChange={handleChange}
					className="w-full border p-3 rounded"
					required
				/>

				<input
					type="text"
					name="author"
					placeholder="Author"
					value={formData.author}
					onChange={handleChange}
					className="w-full border p-3 rounded"
					required
				/>

				<input
					type="number"
					name="quantity"
					placeholder="Quantity"
					value={formData.quantity}
					onChange={handleChange}
					className="w-full border p-3 rounded"
					min="1"
					required
				/>

				{error && <p className="text-red-500">{error}</p>}

				<button
					disabled={loading}
					className="bg-blue-600 text-white px-4 py-2 rounded"
				>
					{loading ? "Saving..." : "Save"}
				</button>
			</form>
		</Modal>
	);
}

export default BookModal;
