package com.invitationcode.generator.domain.memberinvitation.domain;

import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.concurrent.ThreadLocalRandom;

@Embeddable
public class InviteCode {

    private static final int CERT_CHAR_LENGTH = 8;
    private static final char[] CHARACTER_TABLE = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    private static final int CHARACTER_TABLE_LENGTH = CHARACTER_TABLE.length;

    @Comment(value = "초대 코드")
    @Column(name = "invite_code", length = 10, nullable = false)
    private String inviteCode;

    public InviteCode() {
        this.inviteCode = generateNumber();
    }

    private String generateNumber() {
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < CERT_CHAR_LENGTH; i++) {
            Integer randomIndex = random.nextInt(CHARACTER_TABLE_LENGTH);
                Character randomText = CHARACTER_TABLE[randomIndex];
                sb.append(randomText);
        }
        return sb.toString();
    }
}
