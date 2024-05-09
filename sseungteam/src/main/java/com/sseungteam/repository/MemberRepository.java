package com.sseungteam.repository;

import com.sseungteam.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email); // 회원 가입 시 중복 여부 확인
}
