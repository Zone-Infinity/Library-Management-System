import { useEffect, useState } from "react";

import { getAllBooks } from "../api/bookApi";
import { getAllMembers } from "../api/memberApi";
import { getAllTransactions } from "../api/transactionApi";

import Loader from "../components/common/Loader";
import ErrorMessage from "../components/common/ErrorMessage";

import { StatCard } from "../components/StatCard";

function Dashboard() {
	const [stats, setStats] = useState({
		totalBooks: 0,
		totalMembers: 0,
		issuedBooks: 0,
		availableBooks: 0,
	});

	const [recentTransactions, setRecentTransactions] = useState([]);

	const [overdueTransactions, setOverdueTransactions] = useState([]);

	const [loading, setLoading] = useState(true);

	const [error, setError] = useState("");

	useEffect(() => {
		let ignore = false;

		const loadDashboard = async () => {
			try {
				setLoading(true);
				setError("");

				const [books, members, transactions] = await Promise.all([
					getAllBooks(),
					getAllMembers(),
					getAllTransactions(),
				]);

				const issuedBooks = transactions.filter(
					(t) => t.status === "ISSUED",
				).length;

				const availableBooks = books.reduce(
					(sum, book) => sum + book.availableQuantity,
					0,
				);

				const recentIssued = transactions
					.filter((t) => t.status === "ISSUED")
					.sort((a, b) => new Date(b.issueDate) - new Date(a.issueDate))
					.slice(0, 5);

				const overdue = transactions.filter(
					(t) => t.status === "ISSUED" && new Date(t.dueDate) < new Date(),
				);

				if (!ignore) {
					setStats({
						totalBooks: books.length,

						totalMembers: members.length,

						issuedBooks,

						availableBooks,
					});

					setRecentTransactions(recentIssued);

					setOverdueTransactions(overdue);
				}
			} catch (err) {
				if (!ignore) {
					setError(err.message || "Failed to load dashboard");
				}
			} finally {
				if (!ignore) {
					setLoading(false);
				}
			}
		};

		loadDashboard();

		return () => {
			ignore = true;
		};
	}, []);

	if (loading) {
		return <Loader />;
	}

	if (error) {
		return <ErrorMessage message={error} />;
	}

	return (
		<div>
			<h1 className="text-3xl font-bold mb-6">Dashboard</h1>

			<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
				<StatCard title="Total Books" value={stats.totalBooks} />

				<StatCard title="Members" value={stats.totalMembers} />

				<StatCard title="Books Issued" value={stats.issuedBooks} />

				<StatCard title="Available Copies" value={stats.availableBooks} />
			</div>

			<div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
				<div className="bg-white rounded-lg shadow p-6">
					<h2 className="text-xl font-semibold mb-4">Recently Issued</h2>

					{recentTransactions.length === 0 ? (
						<p className="text-gray-500">No recent transactions</p>
					) : (
						<div className="space-y-4">
							{recentTransactions.map((transaction) => (
								<div key={transaction.id} className="border rounded-lg p-4">
									<div className="font-medium">{transaction.bookTitle}</div>

									<div className="text-sm text-gray-600">
										{transaction.memberName}
									</div>

									<div className="text-sm text-gray-500 mt-1">
										Issued: {transaction.issueDate}
									</div>
								</div>
							))}
						</div>
					)}
				</div>

				<div className="bg-white rounded-lg shadow p-6">
					<h2 className="text-xl font-semibold mb-4 text-red-700">
						Overdue Books
					</h2>

					{overdueTransactions.length === 0 ? (
						<p className="text-gray-500">No overdue books</p>
					) : (
						<div className="space-y-4">
							{overdueTransactions.map((transaction) => (
								<div
									key={transaction.id}
									className="border border-red-200 bg-red-50 rounded-lg p-4"
								>
									<div className="font-medium text-red-800">
										{transaction.bookTitle}
									</div>

									<div className="text-sm text-red-700">
										{transaction.memberName}
									</div>

									<div className="text-sm text-red-600 mt-1">
										Due: {transaction.dueDate}
									</div>
								</div>
							))}
						</div>
					)}
				</div>
			</div>
		</div>
	);
}

export default Dashboard;
