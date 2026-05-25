package me.isoham.lmsbackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import me.isoham.lmsbackend.dto.MemberRequest;
import me.isoham.lmsbackend.dto.MemberResponse;
import me.isoham.lmsbackend.entity.Member;
import me.isoham.lmsbackend.mapper.MemberMapper;
import me.isoham.lmsbackend.repository.MemberRepository;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final MemberMapper mapper;

	public MemberService(MemberRepository memberRepository, MemberMapper mapper) {
		this.memberRepository = memberRepository;
		this.mapper = mapper;
	}

	// Fetch all active (not deleted) members
	// and convert them into response DTOs.
	public List<MemberResponse> getAllMembers() {
		return memberRepository.findByActiveTrue().stream().map(mapper::toResponse).toList();
	}

	// Fetch a single member by id.
	//
	// Throws:
	// IllegalArgumentException -> if member does not exist
	public MemberResponse getMemberById(Long id) {

		return mapper.toResponse(memberRepository.findByIdAndActiveTrue(id)
				.orElseThrow(() -> new IllegalArgumentException("Member not found with id " + id)));
	}

	// Create a new member.
	//
	// Validates uniqueness for: name or email or phone
	//
	// Returns all conflicting fields together.
	// Example:
	// "email, phone already exists"
	//
	// Throws:
	// IllegalStateException -> if conflicts exist
	public MemberResponse saveMember(MemberRequest member) {

		List<String> conflicts = new ArrayList<>();

		// Check duplicate name
		if (memberRepository.existsByName(member.getName())) {
			conflicts.add("name");
		}

		// Check duplicate email
		if (memberRepository.existsByEmail(member.getEmail())) {
			conflicts.add("email");
		}

		// Check duplicate phone
		if (memberRepository.existsByPhone(member.getPhone())) {
			conflicts.add("phone");
		}

		// Throw combined validation error
		if (!conflicts.isEmpty()) {
			throw new IllegalStateException(String.join(", ", conflicts) + " already exists");
		}

		return mapper.toResponse(memberRepository.save(mapper.toEntity(member)));
	}

	// Soft delete a member by setting:
	// active = false
	//
	// Throws:
	// IllegalArgumentException -> if member does not exist
	public void deleteMember(Long id) {

		Member book = memberRepository.findByIdAndActiveTrue(id)
				.orElseThrow(() -> new IllegalArgumentException("Member not found by id " + id));

		book.setActive(false);

		memberRepository.save(book);
	}
}