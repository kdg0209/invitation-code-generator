package com.invitationcode.generator.domain.member.service;

import com.invitationcode.generator.domain.member.dao.MemberDao;
import com.invitationcode.generator.domain.member.domain.Email;
import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.member.domain.Password;
import com.invitationcode.generator.domain.member.dto.MemberCreateRequestDto;
import com.invitationcode.generator.domain.member.dto.MemberUpdateRequestDto;
import com.invitationcode.generator.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberDao memberDao;
    private final MemberRepository memberRepository;

    public Long create(MemberCreateRequestDto requestDto) {
        memberDao.verifyDuplicateMemberId(requestDto.getId());

        Password password = new Password(requestDto.getPassword(), passwordEncoder);
        Email email = new Email(requestDto.getEmail());

        Member member = Member.builder()
                .id(requestDto.getId())
                .password(password)
                .name(requestDto.getName())
                .email(email)
                .nickName(requestDto.getNickName())
                .build();

        memberRepository.save(member);
        return member.getIdx();
    }

    public Long update(Long memberIdx, MemberUpdateRequestDto requestDto) {
        Member member = memberDao.findByIdx(memberIdx);
        member.updateNickName(requestDto.getNickName());
        return member.getIdx();
    }

    public void delete(Long memberIdx) {
        Member member = memberDao.findByIdx(memberIdx);
        member.delete();
    }
}
