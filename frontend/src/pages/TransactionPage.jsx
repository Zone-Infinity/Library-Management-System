import { useEffect, useState } from "react";

import { getAllTransactions, returnBook } from "../api/transactionApi";

import { getAllBooks } from "../api/bookApi";

import { getAllMembers } from "../api/memberApi";

import Loader from "../components/common/Loader";
import ErrorMessage from "../components/common/ErrorMessage";
import PageHeader from "../components/common/PageHeader";
import ConfirmModal from "../components/common/ConfirmModal";

import TransactionsTable from "../components/transactions/TransactionsTable";

import IssueBookModal from "../components/transactions/IssueBookModal";

function TransactionsPage() {
	const [transactions, setTransactions] = useState([]);

	const [books, setBooks] = useState([]);

	const [members, setMembers] = useState([]);

	const [loading, setLoading] = useState(true);

	const [error, setError] = useState("");

	const [search, setSearch] = useState("");

	const [showIssueModal, setShowIssueModal] = useState(false);

	const [selectedTransaction, setSelectedTransaction] = useState(null);

	const [returnLoading, setReturnLoading] = useState(false);

	const fetchTransactions = async () => {
		try {
			setLoading(true);
			setError("");

			const [transactionData, bookData, memberData] = await Promise.all([
				getAllTransactions(),
				getAllBooks(),
				getAllMembers(),
			]);

			setTransactions(transactionData);

			setBooks(bookData);

			setMembers(memberData);
		} catch (err) {
			setError(err.message || "Failed to fetch transactions");
		} finally {
			setLoading(false);
		}
	};

	useEffect(() => {
		let ignore = false;

		const loadTransactions = async () => {
			try {
				setLoading(true);
				setError("");

				const [transactionData, bookData, memberData] = await Promise.all([
					getAllTransactions(),
					getAllBooks(),
					getAllMembers(),
				]);

				if (!ignore) {
					setTransactions(transactionData);

					setBooks(bookData);

					setMembers(memberData);
				}
			} catch (err) {
				if (!ignore) {
					setError(
						err.message || "Failed to fetch transactions",
					);
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
	}, []);

	const handleReturnBook = (transaction) => {
		setSelectedTransaction(transaction);
	};

	const confirmReturnBook = async () => {
		if (!selectedTransaction) {
			return;
		}

		try {
			setReturnLoading(true);

			await returnBook({
				memberId: selectedTransaction.memberId,

				bookId: selectedTransaction.bookId,
			});

			setSelectedTransaction(null);

			await fetchTransactions();
		} catch (err) {
			setError(err.message || "Failed to return book");
		} finally {
			setReturnLoading(false);
		}
	};

	const filteredTransactions = transactions.filter((transaction) => {
		const query = search.toLowerCase();

		return (
			transaction.bookTitle.toLowerCase().includes(query) ||
			transaction.memberName.toLowerCase().includes(query)
		);
	});

	return (
		<div>
			<PageHeader title="Transactions" />

			<div className="flex justify-between items-center mb-6 gap-4">
				<input
					type="text"
					placeholder="Search by book or member"
					value={search}
					onChange={(e) => setSearch(e.target.value)}
					className="border p-3 rounded w-full"
				/>

				<button
					onClick={() => setShowIssueModal(true)}
					className="bg-blue-600 text-white px-4 py-3 rounded whitespace-nowrap"
				>
					Issue Book
				</button>
			</div>

			{loading && <Loader />}

			{error && <ErrorMessage message={error} />}

			{!loading && !error && (
				<TransactionsTable
					transactions={filteredTransactions}
					onReturn={handleReturnBook}
				/>
			)}

			{showIssueModal && (
				<IssueBookModal
					members={members}
					books={books}
					onClose={() => setShowIssueModal(false)}
					onSuccess={fetchTransactions}
				/>
			)}

			{selectedTransaction && (
				<ConfirmModal
					title="Confirm Return"
					message={`Return "${selectedTransaction.bookTitle}" for ${selectedTransaction.memberName}?`}
					onConfirm={confirmReturnBook}
					onClose={() => setSelectedTransaction(null)}
					loading={returnLoading}
				/>
			)}
		</div>
	);
}

export default TransactionsPage;
