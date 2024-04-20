package com.shopmax.entity;

import com.shopmax.constant.Role;
import com.shopmax.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //pk값의 타입은 참조 타입(객체) Long 으로 지정

    @Column(unique = true)
    private String email;

    private String name; //String 사이즈 지정 x -> varchar(255)

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String address;

    //MemberFormDto -> Member 엔티티 객체로 변환
    //JPA에서는 영속성 컨텍스트에 엔티티 객체를 통해 CRUD를 진행하므로 반드시 엔티티 객체로 변경시켜줘야 한다.
    public static Member createMember(MemberFormDto memberFormDto) {
        Member member = new Member();

        //사용자가 입력한 회원가입 정보를 member 엔티티로 변환
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPassword(memberFormDto.getPassword());
        member.setAddress(memberFormDto.getAddress());

        //개발자가 지정해줘야 하는 정보
        member.setRole(Role.USER); //일반 사용자로 가입할 때
        //member.setRole(Role.ADMIN); //관리자로 가입할 때

        return member;
    }

}
