package com.example.game.dao;

import com.example.game.dto.Member;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    public Member loginMember(Member member) throws Exception;

    public void chargeBalance(Member member, int charge) throws Exception;

    public void updateBalance(int memberId, int newBalance);

}
