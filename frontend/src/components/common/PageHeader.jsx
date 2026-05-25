function PageHeader({ title, buttonText, onButtonClick }) {
	return (
		<div className="flex justify-between items-center mb-6">
			<h1 className="text-2xl font-bold">{title}</h1>

			{buttonText && (
				<button
					onClick={onButtonClick}
					className="bg-blue-600 text-white px-4 py-2 rounded-md"
				>
					{buttonText}
				</button>
			)}
		</div>
	);
}

export default PageHeader;
