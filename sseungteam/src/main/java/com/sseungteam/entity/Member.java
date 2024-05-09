package com.sseungteam.entity;

import com.sseungteam.constant.Role;
import com.sseungteam.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name= "member")
@Getter
@Setter
@ToString
public class Member {
    @Id
    @Column(name= "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;
    private int balance;
    private String quiz;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        //패스워드 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword());

        Member member = new Member();

        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setQuiz(memberFormDto.getQuiz());
        //암호화한 pw를 DB에 저장 시키기 위해 암호화로 만들어둔 password를 매개변수로 넣기
        member.setPassword(password);

        //개발자 지정하는 정보
        //member.setRole(Role.USER); //일반유저
        member.setRole(Role.ADMIN); //관리자
        member.setBalance(10000);

        return member;
    }

}
