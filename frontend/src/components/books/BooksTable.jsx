function BooksTable({ books, onAdd, onRemove, onDelete }) {
	return (
		<div className="bg-white rounded-lg shadow overflow-hidden">
			<table className="w-full">
				<thead className="bg-gray-100">
					<tr>
						<th className="text-left p-4">Title</th>

						<th className="text-left p-4">Author</th>

						<th className="text-left p-4">Quantity</th>

						<th className="text-left p-4">Available</th>

						<th className="text-left p-4">Actions</th>
					</tr>
				</thead>

				<tbody>
					{books.length === 0 ? (
						<tr>
							<td colSpan="5" className="text-center py-10 text-gray-500">
								No books found
							</td>
						</tr>
					) : (
						books.map((book) => (
							<tr key={book.id} className="border-t">
								<td className="p-4">{book.title}</td>

								<td className="p-4">{book.author}</td>

								<td className="p-4">{book.quantity}</td>

								<td className="p-4">{book.availableQuantity}</td>

								<td className="p-4">
									<div className="flex gap-2 flex-wrap">
										<button
											onClick={() => onAdd(book)}
											className="bg-green-600 text-white px-3 py-1 rounded"
										>
											+ Stock
										</button>

										<button
											onClick={() => onRemove(book)}
											className="bg-yellow-600 text-white px-3 py-1 rounded"
										>
											- Stock
										</button>

										<button
											onClick={() => onDelete(book)}
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

export default BooksTable;
