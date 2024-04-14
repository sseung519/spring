package com.example.game.service;

import com.example.game.dao.GamesDao;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    MemberService memberService;
    @Autowired
    GamesDao gamesDao;

    // 게임 구매 메서드
    @Override
    public boolean purchaseGame(HttpSession session, int gNo, int gPrice) {
        try {
            int memberId = (int) session.getAttribute("member_id");
            int balance = (int) session.getAttribute("balance");

            if (balance >= gPrice) {
                balance -= gPrice;
                session.setAttribute("balance", balance);

                // 사용자의 잔고를 업데이트합니다.
                memberService.updateBalance(memberId, balance);


                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

