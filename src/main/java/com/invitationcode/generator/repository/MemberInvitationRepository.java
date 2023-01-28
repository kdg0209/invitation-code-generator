package com.invitationcode.generator.repository;

import com.invitationcode.generator.domain.MemberInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInvitationRepository extends JpaRepository<MemberInvitation, Long> {
}
