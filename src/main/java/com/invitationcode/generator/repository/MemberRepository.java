package com.invitationcode.generator.repository;

import com.invitationcode.generator.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
