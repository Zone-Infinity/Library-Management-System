function MembersTable({ members, onEdit, onDelete, onViewTransactions }) {
	return (
		<div className="bg-white rounded-lg shadow overflow-hidden">
			<table className="w-full">
				<thead className="bg-gray-100">
					<tr>
						<th className="text-left p-4">Name</th>

						<th className="text-left p-4">Email</th>

						<th className="text-left p-4">Phone</th>

						<th className="text-left p-4">Actions</th>
					</tr>
				</thead>

				<tbody>
					{members.length === 0 ? (
						<tr>
							<td colSpan="4" className="text-center py-10 text-gray-500">
								No members found
							</td>
						</tr>
					) : (
						members.map((member) => (
							<tr key={member.id} className="border-t">
								<td className="p-4">{member.name}</td>

								<td className="p-4">{member.email}</td>

								<td className="p-4">{member.phone}</td>

								<td className="p-4">
									<div className="flex gap-2 flex-wrap">
										<button
											onClick={() => onViewTransactions(member)}
											className="bg-blue-600 text-white px-3 py-1 rounded"
										>
											Transactions
										</button>

										<button
											onClick={() => onEdit(member)}
											className="bg-yellow-500 text-white px-3 py-1 rounded"
										>
											Edit
										</button>

										<button
											onClick={() => onDelete(member)}
											className="bg-red-600 text-white px-3 py-1 rounded"
										>
											Delete
										</button>
									</div>
								</td>
							</tr>
						))
					)}
				</tbody>
			</table>
		</div>
	);
}

export default MembersTable;
