import Modal from "./Modal";

function ConfirmModal({ title, message, onConfirm, onClose, loading = false }) {
	return (
		<Modal title={title} onClose={onClose}>
			<div className="space-y-6">
				<p className="text-gray-700">{message}</p>

				<div className="flex justify-end gap-3">
					<button
						onClick={onClose}
						className="px-4 py-2 border rounded"
						disabled={loading}
					>
						Cancel
					</button>

					<button
						onClick={onConfirm}
						className="bg-red-600 text-white px-4 py-2 rounded"
						disabled={loading}
					>
						{loading ? "Processing..." : "Confirm"}
					</button>
				</div>
			</div>
		</Modal>
	);
}

export default ConfirmModal;
