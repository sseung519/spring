package com.shopmax.service;

import com.shopmax.dto.MemberFormDto;
import com.shopmax.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
//@Rollback(value = false)
@TestPropertySource(locations="classpath:application-test.properties")
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    //회원가입
    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test1@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");

        return Member.createMember(memberFormDto, passwordEncoder);
    }

    //회원 중복체크
    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        //회원가입시킨 정보와 DB에 저장된 회원가입 정보가 일치하는지 확인
        Assertions.assertEquals(member.getEmail(), savedMember.getEmail());
        Assertions.assertEquals(member.getName(), savedMember.getName());
        Assertions.assertEquals(member.getAddress(), savedMember.getAddress());
        Assertions.assertEquals(member.getRole(), savedMember.getRole());
        Assertions.assertEquals(member.getPassword(), savedMember.getPassword());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void validateDuplicateMemberTest() {
        Member member1 = createMember();
        Member member2 = createMember();

        memberService.saveMember(member1);

        Throwable e = Assertions.assertThrows(IllegalStateException.class, () ->{
           memberService.saveMember(member2);
        });

        Assertions.assertEquals("이미 가입된 이메일입니다.", e.getMessage());
    }
}
