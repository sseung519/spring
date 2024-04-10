package com.example.game.service;

import com.example.game.dao.MemberDao;
import com.example.game.dto.Member;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public Member loginMember(Member member) throws Exception {
        return memberDao.loginMember(member);
    }

    @Override
    public void chargeBalance(Member member, int charge) throws Exception {
        memberDao.chargeBalance(member, charge);
    }
}
