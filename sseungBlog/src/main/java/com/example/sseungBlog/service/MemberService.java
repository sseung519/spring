package com.example.sseungBlog.service;

import com.example.sseungBlog.dto.Member;

public interface MemberService {
    public Member loginMember(Member member) throws Exception;
    public void registerMember(Member member) throws Exception;

}
