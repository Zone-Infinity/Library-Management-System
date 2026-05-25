import { useState } from "react";

import Modal from "../common/Modal";

import { addMember, updateMember } from "../../api/memberApi";

function MemberModal({ member, onClose, onSuccess }) {
	const [formData, setFormData] = useState(() => ({
		id: member?.id || null,
		name: member?.name || "",
		email: member?.email || "",
		phone: member?.phone || "",
	}));

	const [loading, setLoading] = useState(false);

	const [error, setError] = useState("");

	const handleChange = (e) => {
		setFormData({
			...formData,
			[e.target.name]: e.target.value,
		});
	};

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			setLoading(true);
			setError("");

			if (member) {
				await updateMember(formData);
			} else {
				await addMember(formData);
			}

			onSuccess();
			onClose();
		} catch (err) {
			setError(err.message || "Failed to save member");
		} finally {
			setLoading(false);
		}
	};

	return (
		<Modal title={member ? "Edit Member" : "Add Member"} onClose={onClose}>
			<form onSubmit={handleSubmit} className="space-y-4">
				<input
					type="text"
					name="name"
					placeholder="Name"
					value={formData.name}
					onChange={handleChange}
					className="w-full border p-3 rounded"
					required
				/>

				<input
					type="email"
					name="email"
					placeholder="Email"
					value={formData.email}
					onChange={handleChange}
					className="w-full border p-3 rounded"
					required
				/>

				<input
					type="text"
					name="phone"
					placeholder="Phone"
					value={formData.phone}
					onChange={handleChange}
					className="w-full border p-3 rounded"
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

export default MemberModal;
