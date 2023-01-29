package com.invitationcode.generator.domain.member.repository;

import com.invitationcode.generator.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
