package com.example.game.service;

import com.example.game.dto.Member;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

public interface MemberService {
    public Member loginMember(Member member) throws Exception;

    public void chargeBalance(Member member, int charge) throws Exception;

    public void updateBalance(int memberId, int newBalance);

}
