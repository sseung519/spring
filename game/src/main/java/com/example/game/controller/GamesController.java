package com.example.game.controller;

import com.example.game.dto.Games;
import com.example.game.dto.Library;
import com.example.game.dto.Member;
import com.example.game.service.GamesService;
import com.example.game.service.LibraryGameService;
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
    @Autowired
    LibraryGameService libraryGameService;

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
    public String showPurchasePage(@PathVariable("gNo") int gNo, Model model) {
        Games game = gamesService.getGameByNo(gNo);
        model.addAttribute("game", game);
        return "purchasePage";
    }

    @PostMapping("/purchase/{gNo}")
    public String purchaseGame(@PathVariable("gNo") int gNo, HttpSession session, @RequestParam("gPrice") int gPrice, RedirectAttributes redirectAttributes) {
        boolean purchaseSuccess = purchaseService.purchaseGame(session, gNo, gPrice);

        if (purchaseSuccess) {
//            Member member = (Member) session.getAttribute("member");
//            Games games = (Games) session.getAttribute("games");
//            Library library = (Library) session.getAttribute("library");
//            libraryGameService.addGameToLibrary(member, games, library);

            redirectAttributes.addFlashAttribute("successMessage", "게임을 구매하였습니다.");

        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "잔액이 부족하여 게임을 구매할 수 없습니다.");
        }

        return "redirect:/games/" + gNo;
    }

}