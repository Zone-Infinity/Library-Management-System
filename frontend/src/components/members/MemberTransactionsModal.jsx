import { useEffect, useState } from "react";

import { getMemberTransactions } from "../../api/memberApi";

import Modal from "../common/Modal";
import Loader from "../common/Loader";
import ErrorMessage from "../common/ErrorMessage";

function MemberTransactionsModal({ member, onClose }) {
	const [transactions, setTransactions] = useState([]);

	const [loading, setLoading] = useState(true);

	const [error, setError] = useState("");

	useEffect(() => {
		let ignore = false;

		const loadTransactions = async () => {
			try {
				setLoading(true);
				setError("");

				const data = await getMemberTransactions(member.id);

				if (!ignore) {
					setTransactions(data);
				}
			} catch (err) {
				if (!ignore) {
					setError(err.message || "Failed to load transactions");
				}
			} finally {
				if (!ignore) {
					setLoading(false);
				}
			}
		};

		loadTransactions();

		return () => {
			ignore = true;
		};
	}, [member.id]);

	const isOverdue = (transaction) => {
		if (transaction.status !== "ISSUED") {
			return false;
		}

		return new Date(transaction.dueDate) < new Date();
	};

	const getStatusColor = (transaction) => {
		if (isOverdue(transaction)) {
			return "bg-red-200 text-red-800";
		}

		switch (transaction.status) {
			case "ISSUED":
				return "bg-yellow-100 text-yellow-700";

			case "RETURNED":
				return "bg-green-100 text-green-700";

			default:
				return "bg-gray-100 text-gray-700";
		}
	};

	const getStatusLabel = (transaction) => {
		if (isOverdue(transaction)) {
			return "OVERDUE";
		}

		return transaction.status;
	};

	return (
		<Modal title={`${member.name} Transactions`} onClose={onClose}>
			{loading && <Loader />}

			{error && <ErrorMessage message={error} />}

			{!loading && !error && (
				<div className="overflow-x-auto">
					<table className="w-full border">
						<thead className="bg-gray-100">
							<tr>
								<th className="text-left p-3">Book</th>

								<th className="text-left p-3">Issue Date</th>

								<th className="text-left p-3">Due Date</th>

								<th className="text-left p-3">Return Date</th>

								<th className="text-left p-3">Status</th>
							</tr>
						</thead>

						<tbody>
							{transactions.length === 0 ? (
								<tr>
									<td colSpan="5" className="text-center p-6 text-gray-500">
										No transactions found
									</td>
								</tr>
							) : (
								transactions.map((transaction) => (
									<tr
										key={transaction.id}
										className={`border-t ${
											isOverdue(transaction) ? "bg-red-50" : ""
										}`}
									>
										<td className="p-3">{transaction.bookTitle}</td>

										<td className="p-3">{transaction.issueDate}</td>

										<td className="p-3">{transaction.dueDate}</td>

										<td className="p-3">{transaction.returnDate || "-"}</td>

										<td className="p-3">
											<span
												className={`px-2 py-1 rounded text-sm font-medium ${getStatusColor(
													transaction,
												)}`}
											>
												{getStatusLabel(transaction)}
											</span>
										</td>
									</tr>
								))
							)}
						</tbody>
					</table>
				</div>
			)}
		</Modal>
	);
}

export default MemberTransactionsModal;
