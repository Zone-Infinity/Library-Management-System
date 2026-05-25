import { useState } from "react";

import Modal from "../common/Modal";

import { issueBook } from "../../api/transactionApi";

function IssueBookModal({ members, books, onClose, onSuccess }) {
	const [memberId, setMemberId] = useState("");

	const [bookId, setBookId] = useState("");

	const [loading, setLoading] = useState(false);

	const [error, setError] = useState("");

	const availableBooks = books.filter((book) => book.availableQuantity > 0);

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			setLoading(true);
			setError("");

			await issueBook({
				memberId: Number(memberId),
				bookId: Number(bookId),
			});

			onSuccess();
			onClose();
		} catch (err) {
			setError(err.message || "Failed to issue book");
		} finally {
			setLoading(false);
		}
	};

	return (
		<Modal title="Issue Book" onClose={onClose}>
			<form onSubmit={handleSubmit} className="space-y-4">
				<select
					value={memberId}
					onChange={(e) => setMemberId(e.target.value)}
					className="w-full border p-3 rounded"
					required
				>
					<option value="">Select Member</option>

					{members.map((member) => (
						<option key={member.id} value={member.id}>
							{member.name}
						</option>
					))}
				</select>

				<select
					value={bookId}
					onChange={(e) => setBookId(e.target.value)}
					className="w-full border p-3 rounded"
					required
				>
					<option value="">Select Book</option>

					{availableBooks.map((book) => (
						<option key={book.id} value={book.id}>
							{book.title} ({book.availableQuantity} available)
						</option>
					))}
				</select>

				{error && <p className="text-red-500">{error}</p>}

				<button
					disabled={loading}
					className="bg-blue-600 text-white px-4 py-2 rounded"
				>
					{loading ? "Processing..." : "Issue Book"}
				</button>
			</form>
		</Modal>
	);
}

export default IssueBookModal;
