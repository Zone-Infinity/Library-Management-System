import { Link } from "react-router-dom";

function Navbar() {
	return (
		<nav className="bg-blue-600 text-white px-6 py-4">
			<div className="flex gap-6">
				<Link to="/">Dashboard</Link>
				<Link to="/books">Books</Link>
				<Link to="/members">Members</Link>
				<Link to="/transactions">Transactions</Link>
			</div>
		</nav>
	);
}

export default Navbar;
