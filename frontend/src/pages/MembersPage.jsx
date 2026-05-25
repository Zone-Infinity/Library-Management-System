import { useEffect, useState } from "react";

import { getAllMembers, deleteMember } from "../api/memberApi";

import Loader from "../components/common/Loader";
import ErrorMessage from "../components/common/ErrorMessage";
import PageHeader from "../components/common/PageHeader";
import ConfirmModal from "../components/common/ConfirmModal";

import MembersTable from "../components/members/MembersTable";
import MemberModal from "../components/members/MemberModal";
import MemberTransactionsModal from "../components/members/MemberTransactionsModal";

function MembersPage() {
	const [members, setMembers] = useState([]);

	const [loading, setLoading] = useState(true);

	const [error, setError] = useState("");

	const [showModal, setShowModal] = useState(false);

	const [selectedMember, setSelectedMember] = useState(null);

	const [memberToDelete, setMemberToDelete] = useState(null);

	const [deleteLoading, setDeleteLoading] = useState(false);

	const [transactionMember, setTransactionMember] = useState(null);

	const fetchMembers = async () => {
		try {
			setLoading(true);
			setError("");

			const data = await getAllMembers();

			setMembers(data);
		} catch (err) {
			setError(err.message || "Failed to fetch members");
		} finally {
			setLoading(false);
		}
	};

	useEffect(() => {
		let ignore = false;

		const loadMembers = async () => {
			try {
				setLoading(true);
				setError("");

				const data = await getAllMembers();

				if (!ignore) {
					setMembers(data);
				}
			} catch (err) {
				if (!ignore) {
					setError(err.message || "Failed to fetch members");
				}
			} finally {
				if (!ignore) {
					setLoading(false);
				}
			}
		};

		loadMembers();

		return () => {
			ignore = true;
		};
	}, []);

	const handleAddMember = () => {
		setSelectedMember(null);
		setShowModal(true);
	};

	const handleEditMember = (member) => {
		setSelectedMember(member);
		setShowModal(true);
	};

	const handleDeleteMember = (member) => {
		setMemberToDelete(member);
	};

	const handleViewTransactions = (member) => {
		setTransactionMember(member);
	};

	const confirmDeleteMember = async () => {
		if (!memberToDelete) {
			return;
		}

		try {
			setDeleteLoading(true);

			await deleteMember(memberToDelete.id);

			setMemberToDelete(null);

			await fetchMembers();
		} catch (err) {
			setError(err.message || "Failed to delete member");
		} finally {
			setDeleteLoading(false);
		}
	};

	return (
		<div>
			<PageHeader
				title="Members"
				buttonText="Add Member"
				onButtonClick={handleAddMember}
			/>

			{loading && <Loader />}

			{error && <ErrorMessage message={error} />}

			{!loading && !error && (
				<MembersTable
					members={members}
					onEdit={handleEditMember}
					onDelete={handleDeleteMember}
					onViewTransactions={handleViewTransactions}
				/>
			)}

			{showModal && (
				<MemberModal
					member={selectedMember}
					onClose={() => {
						setShowModal(false);

						setSelectedMember(null);
					}}
					onSuccess={fetchMembers}
				/>
			)}

			{memberToDelete && (
				<ConfirmModal
					title="Delete Member"
					message={`Delete "${memberToDelete.name}"?`}
					onConfirm={confirmDeleteMember}
					onClose={() => setMemberToDelete(null)}
					loading={deleteLoading}
				/>
			)}

			{transactionMember && (
				<MemberTransactionsModal
					member={transactionMember}
					onClose={() => setTransactionMember(null)}
				/>
			)}
		</div>
	);
}

export default MembersPage;
