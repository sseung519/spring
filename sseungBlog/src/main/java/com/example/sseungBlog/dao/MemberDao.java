package com.example.sseungBlog.dao;

import com.example.sseungBlog.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    public Member loginMember(Member member) throws Exception;
    public void registerMember(Member member) throws Exception;
}
