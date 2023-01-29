package com.invitationcode.generator.domain.memberinvitation.repository;

import com.invitationcode.generator.domain.memberinvitation.domain.MemberInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInvitationRepository extends JpaRepository<MemberInvitation, Long> {
}
