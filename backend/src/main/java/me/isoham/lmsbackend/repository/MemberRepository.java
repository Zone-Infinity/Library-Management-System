package me.isoham.lmsbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.isoham.lmsbackend.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findByActiveTrue();

	Optional<Member> findByIdAndActiveTrue(Long id);

	boolean existsByName(String name);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);
}
