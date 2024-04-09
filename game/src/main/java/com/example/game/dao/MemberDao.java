package com.example.game.dao;

import com.example.game.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    public Member loginMember(Member member) throws Exception;
}
