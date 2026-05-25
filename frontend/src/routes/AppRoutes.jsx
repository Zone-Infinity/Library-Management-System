import { BrowserRouter, Routes, Route } from "react-router-dom";

import MainLayout from "../layouts/MainLayout";
import BooksPage from "../pages/BooksPage";
import MembersPage from "../pages/MembersPage";
import TransactionsPage from "../pages/TransactionPage";
import Dashboard from "../pages/Dashboard";

function AppRoutes() {
	return (
		<BrowserRouter>
			<Routes>
				<Route element={<MainLayout />}>
					<Route path="/" element={<Dashboard />} />

					<Route path="/books" element={<BooksPage />} />
					<Route path="/members" element={<MembersPage />} />
					<Route path="/transactions" element={<TransactionsPage />} />
				</Route>
			</Routes>
		</BrowserRouter>
	);
}

export default AppRoutes;
