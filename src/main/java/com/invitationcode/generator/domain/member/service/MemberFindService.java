package com.invitationcode.generator.domain.member.service;

import com.invitationcode.generator.domain.member.dao.MemberDao;
import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.member.dto.MemberFindResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberFindService {

    private final MemberDao memberDao;

    public MemberFindResponseDto find(Long memberIdx) {
        Member member = memberDao.findByIdx(memberIdx);
        return new MemberFindResponseDto(member);
    }
}
