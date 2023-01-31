package com.invitationcode.generator.domain.memberinvitation.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiverEmail {

    private static final String REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    @Comment(value = "받는 사람 이메일 주")
    @Column(name = "receiver_email", nullable = false)
    private String receiverEmail;

    public ReceiverEmail(String receiverEmail) {
        setReceiverEmail(receiverEmail);
    }

    private void setReceiverEmail(String receiverEmail) {
        if (!receiverEmail.matches(REGEX)) {
            throw new IllegalArgumentException();
        }
        this.receiverEmail = receiverEmail;
    }
}
