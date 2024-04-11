package com.example.game.controller;

import com.example.game.dto.Games;
import com.example.game.service.GamesService;
import com.example.game.service.MemberService;
import com.example.game.service.PurchaseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class GamesController {
    @Autowired
    MemberService memberService;
    @Autowired
    GamesService gamesService;
    @Autowired
    PurchaseService purchaseService;

    @GetMapping(value = "/")
    public String index(Model model) {
        return "index";
    }


//    @GetMapping(value = "/games")
//    public String games(Model model){
//        return "games/games";
//    }
    @PostMapping(value = "/games")
    public String games2(Model model){
        return "games/games";
    }

    @GetMapping(value = "/games")
    public String getAllGames(Model model) {
        List<Games> games = gamesService.getAllGames();
        model.addAttribute("games", games);
        return "games/games";
    }

    @GetMapping("/games/{gNo}")
    public String getGameDetail(@PathVariable("gNo") int gNo, Model model) {
        Games game = gamesService.getGameByNo(gNo);
        model.addAttribute("game", game);
        return "games/detail";
    }
    @GetMapping("/purchase/{gNo}")
    public String purchaseGame(@PathVariable("gNo") int gNo, HttpSession httpSession, int gPrice, RedirectAttributes redirectAttributes) {
        // 게임을 구매하고 라이브러리에 추가하는 메서드 호출
        boolean purchaseSuccess = purchaseService.purchaseGame(httpSession, gNo, gPrice);

        if (purchaseSuccess) {
            redirectAttributes.addFlashAttribute("successMessage", "게임을 구매하였습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "잔액이 부족하여 게임을 구매할 수 없습니다.");
        }

        // 구매 후 메인 페이지로 리다이렉트합니다.
        return "redirect:/";
    }

}
