package me.isoham.lmsbackend.mapper;

import org.springframework.stereotype.Component;

import me.isoham.lmsbackend.dto.MemberRequest;
import me.isoham.lmsbackend.dto.MemberResponse;
import me.isoham.lmsbackend.entity.Member;

@Component
public class MemberMapper {

	public MemberResponse toResponse(Member member) {
		return new MemberResponse(member.getId(), member.getName(), member.getEmail(), member.getPhone());
	}

	public Member toEntity(MemberRequest request) {
		return new Member(request.getId(), request.getName(), request.getEmail(), request.getPhone(), true);
	}
}
