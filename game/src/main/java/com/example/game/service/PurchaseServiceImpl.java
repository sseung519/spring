package com.example.game.service;

import com.example.game.dao.GamesDao;
import com.example.game.dto.Games;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.game.dto.Library;
import com.example.game.dto.Member;
import com.example.game.dto.LibraryGame;


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

    // 라이브러리에 게임 추가하는 메소드
    @Override
    public void addToLibrary(HttpSession httpSession, int gNo) {
        // HttpSession에서 사용자의 라이브러리 정보 가져오기
        Library library = (Library) httpSession.getAttribute("library");
        if (library == null) {
            // 라이브러리가 없으면 새로 생성
            library = new Library();
            // Member 객체 생성 후 HttpSession에 저장
            Member member = new Member();
            httpSession.setAttribute("member", member);
            library.setMember(member);
            httpSession.setAttribute("library", library);
        }

        // LibraryGame 객체 생성 후 게임 정보와 라이브러리 정보 설정
        LibraryGame libraryGame = new LibraryGame();
        // Games 객체 생성 후 gNo 설정
        Games games = new Games();
        games.setGNo(gNo);
        libraryGame.setGames(games);
        libraryGame.setLibrary(library);
    }
}

