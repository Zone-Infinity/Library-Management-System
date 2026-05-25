import { X } from "lucide-react";

function Modal({ title, children, onClose }) {
	return (
		<div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
			<div className="bg-white rounded-lg w-full max-w-3xl max-h-[90vh] overflow-y-auto relative shadow-lg">
				<div className="sticky top-0 bg-white border-b px-6 py-4 flex items-center justify-between">
					<h2 className="text-xl font-semibold">{title}</h2>

					<button onClick={onClose} className="text-gray-500 hover:text-black">
						<X size={20} />
					</button>
				</div>

				<div className="p-6">{children}</div>
			</div>
		</div>
	);
}

export default Modal;
