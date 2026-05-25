import { useState } from "react";

import Modal from "../common/Modal";

function QuantityModal({ title, onSubmit, onClose }) {
	const [quantity, setQuantity] = useState(1);

	const [loading, setLoading] = useState(false);

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			setLoading(true);

			await onSubmit(quantity);

			onClose();
		} finally {
			setLoading(false);
		}
	};

	return (
		<Modal title={title} onClose={onClose}>
			<form onSubmit={handleSubmit} className="space-y-4">
				<input
					type="number"
					min="1"
					value={quantity}
					onChange={(e) => setQuantity(Number(e.target.value))}
					className="w-full border p-3 rounded"
					required
				/>

				<button
					disabled={loading}
					className="bg-blue-600 text-white px-4 py-2 rounded"
				>
					{loading ? "Processing..." : "Submit"}
				</button>
			</form>
		</Modal>
	);
}

export default QuantityModal;
