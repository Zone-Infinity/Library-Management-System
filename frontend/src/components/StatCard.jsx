export const StatCard = ({ title, value }) => (
		<div className="bg-white p-6 rounded-lg shadow">
			<h2 className="text-gray-500 text-sm">{title}</h2>

			<p className="text-3xl font-bold mt-2">{value}</p>
		</div>
	);