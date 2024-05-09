package com.shopmax.config;

import com.shopmax.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class MemberContext extends User {
    private final String name;

    public MemberContext(Member member, List<GrantedAuthority> authorities) {
        super(member.getEmail(), member.getPassword(), authorities);
        this.name = member.getName();
    }

}
