function TransactionsTable({ transactions, onReturn }) {
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
		<div className="bg-white rounded-lg shadow overflow-hidden">
			<table className="w-full">
				<thead className="bg-gray-100">
					<tr>
						<th className="text-left p-4">Book</th>

						<th className="text-left p-4">Member</th>

						<th className="text-left p-4">Issue Date</th>

						<th className="text-left p-4">Due Date</th>

						<th className="text-left p-4">Return Date</th>

						<th className="text-left p-4">Status</th>

						<th className="text-left p-4">Actions</th>
					</tr>
				</thead>

				<tbody>
					{transactions.map((transaction) => (
						<tr
							key={transaction.id}
							className={`border-t ${
								isOverdue(transaction) ? "bg-red-50" : ""
							}`}
						>
							<td className="p-4">{transaction.bookTitle}</td>

							<td className="p-4">{transaction.memberName}</td>

							<td className="p-4">{transaction.issueDate}</td>

							<td className="p-4">{transaction.dueDate}</td>

							<td className="p-4">{transaction.returnDate || "-"}</td>

							<td className="p-4">
								<span
									className={`px-2 py-1 rounded text-sm font-medium ${getStatusColor(
										transaction,
									)}`}
								>
									{getStatusLabel(transaction)}
								</span>
							</td>

							<td className="p-4">
								{transaction.status === "ISSUED" && (
									<button
										onClick={() => onReturn(transaction)}
										className="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700"
									>
										Return
									</button>
								)}
							</td>
						</tr>
					))}
				</tbody>
			</table>
		</div>
	);
}

export default TransactionsTable;
