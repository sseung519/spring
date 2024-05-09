package com.sseungteam.service;

import com.sseungteam.entity.Member;
import com.sseungteam.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    //잔고 충전
    public void chargeBalance(String email, int charge) {
        Member member = memberRepository.findByEmail(email);
        member.setBalance(member.getBalance() + charge);
        memberRepository.save(member);
    }

    //header 잔고 이름 출력
    public Member getMember(String email) {
        return memberRepository.findByEmail(email);
    }

    //회원가입
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        //회원 중복체크 후 null이면 회원가입 insert
        return memberRepository.save(member);
    }

    //회원 중복체크
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());

        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 이메일 입니다.");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //해당 email 계정을 가진 사용자가 있는지 확인
        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
