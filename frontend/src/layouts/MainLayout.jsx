import { Outlet } from "react-router-dom";
import Navbar from "../components/common/Navbar";

function MainLayout() {
	return (
		<div className="min-h-screen bg-gray-100">
			<Navbar />

			<main className="p-6">
				<Outlet />
			</main>
		</div>
	);
}

export default MainLayout;
