package com.invitationcode.generator.domain.member.dao;

import com.invitationcode.generator.domain.member.domain.MemberHasCoupon;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.invitationcode.generator.domain.member.domain.QMemberHasCoupon.memberHasCoupon;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberHasCouponDao {

    private final JPAQueryFactory queryFactory;

    public List<MemberHasCoupon> findByMemberIdx(long memberIdx, Set<Long> hasCouponIdx) {
        return queryFactory
                .selectFrom(memberHasCoupon)
                .where(
                        memberHasCoupon.member.idx.eq(memberIdx),
                        memberHasCoupon.idx.in(hasCouponIdx)
                )
                .fetch();
    }
}
