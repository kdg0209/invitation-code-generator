package com.invitationcode.generator.domain.member.dao;

import com.invitationcode.generator.global.exception.BusinessException;
import com.invitationcode.generator.global.exception.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.invitationcode.generator.domain.member.domain.QMember.member;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberDao {

    private final JPAQueryFactory queryFactory;

    public void verifyDuplicateMemberId(String id) {
        Integer result = queryFactory
                .selectOne()
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();

        if (result != null) {
            throw new BusinessException(ErrorCode.DUPLICATED_MEMBER_ID);
        }
    }
}
