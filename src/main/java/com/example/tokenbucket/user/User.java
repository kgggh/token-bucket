package com.example.tokenbucket.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private Long id;
    private String name;
    private Integer age;
    private MemberShipLevel memberShipLevel;

    @Builder
    public User(Long id, String name, Integer age, MemberShipLevel memberShipLevel) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.memberShipLevel = memberShipLevel;
    }

    @Getter
    public enum MemberShipLevel {
        BASIC,
        PREMIUM,
        VIP;

        public static MemberShipLevel getMemberShipLevel(int i) {
            return MemberShipLevel.values()[i];
        }
    }
}
