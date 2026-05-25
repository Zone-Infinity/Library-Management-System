import { useCallback, useEffect, useState } from "react";

import {
	getAllBooks,
	addBookQuantity,
	removeBookQuantity,
	deleteBook,
} from "../api/bookApi";

import Loader from "../components/common/Loader";
import ErrorMessage from "../components/common/ErrorMessage";
import PageHeader from "../components/common/PageHeader";
import ConfirmModal from "../components/common/ConfirmModal";

import BooksTable from "../components/books/BooksTable";
import BookModal from "../components/books/BookModal";
import QuantityModal from "../components/books/QuantityModal";

function BooksPage() {
	const [books, setBooks] = useState([]);

	const [loading, setLoading] = useState(true);

	const [error, setError] = useState("");

	const [showBookModal, setShowBookModal] = useState(false);

	const [quantityAction, setQuantityAction] = useState(null);

	const [selectedBook, setSelectedBook] = useState(null);

	const [bookToDelete, setBookToDelete] = useState(null);

	const [deleteLoading, setDeleteLoading] = useState(false);

	const fetchBooks = useCallback(async () => {
		try {
			setLoading(true);
			setError("");

			const data = await getAllBooks();

			setBooks(data);
		} catch (err) {
			setError(err.message || "Failed to fetch books");
		} finally {
			setLoading(false);
		}
	}, []);

	useEffect(() => {
		let ignore = false;

		const loadBooks = async () => {
			try {
				setLoading(true);
				setError("");

				const data = await getAllBooks();

				if (!ignore) {
					setBooks(data);
				}
			} catch (err) {
				if (!ignore) {
					setError(err.message || "Failed to fetch books");
				}
			} finally {
				if (!ignore) {
					setLoading(false);
				}
			}
		};

		loadBooks();

		return () => {
			ignore = true;
		};
	}, []);

	const handleAddBook = () => {
		setSelectedBook(null);
		setShowBookModal(true);
	};

	const handleAddStock = (book) => {
		setSelectedBook(book);
		setQuantityAction("add");
	};

	const handleRemoveStock = (book) => {
		setSelectedBook(book);
		setQuantityAction("remove");
	};

	const handleDeleteBook = (book) => {
		setBookToDelete(book);
	};

	const confirmDeleteBook = async () => {
		if (!bookToDelete) {
			return;
		}

		try {
			setDeleteLoading(true);

			await deleteBook(bookToDelete.id);

			setBookToDelete(null);

			await fetchBooks();
		} catch (err) {
			setError(err.message || "Failed to delete book");
		} finally {
			setDeleteLoading(false);
		}
	};

	const closeQuantityModal = () => {
		setQuantityAction(null);
		setSelectedBook(null);
	};

	return (
		<div>
			<PageHeader
				title="Books"
				buttonText="Add Book"
				onButtonClick={handleAddBook}
			/>

			{loading && <Loader />}

			{error && <ErrorMessage message={error} />}

			{!loading && !error && (
				<BooksTable
					books={books}
					onAdd={handleAddStock}
					onRemove={handleRemoveStock}
					onDelete={handleDeleteBook}
				/>
			)}

			{showBookModal && (
				<BookModal
					book={selectedBook}
					onClose={() => {
						setShowBookModal(false);

						setSelectedBook(null);
					}}
					onSuccess={fetchBooks}
				/>
			)}

			{quantityAction &&
				selectedBook &&
				(quantityAction === "add" || quantityAction === "remove") && (
					<QuantityModal
						title={
							quantityAction === "add"
								? `Add Stock - ${selectedBook.title}`
								: `Remove Stock - ${selectedBook.title}`
						}
						onClose={closeQuantityModal}
						onSubmit={async (quantity) => {
							if (quantityAction === "add") {
								await addBookQuantity(selectedBook.id, quantity);
							} else {
								await removeBookQuantity(selectedBook.id, quantity);
							}

							closeQuantityModal();

							await fetchBooks();
						}}
					/>
				)}

			{bookToDelete && (
				<ConfirmModal
					title="Delete Book"
					message={`Delete "${bookToDelete.title}"?`}
					onConfirm={confirmDeleteBook}
					onClose={() => setBookToDelete(null)}
					loading={deleteLoading}
				/>
			)}
		</div>
	);
}

export default BooksPage;
