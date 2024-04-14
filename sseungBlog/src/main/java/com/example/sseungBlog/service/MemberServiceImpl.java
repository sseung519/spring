package com.example.sseungBlog.service;

import com.example.sseungBlog.dao.MemberDao;
import com.example.sseungBlog.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    MemberDao memberDao;

    @Override
    public Member loginMember(Member member) throws Exception {
        return memberDao.loginMember(member);
    }

    @Override
    public void registerMember(Member member) throws Exception {
       memberDao.registerMember(member);
    }
}
