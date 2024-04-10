package com.example.game.controller;

import com.example.game.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GamesController {
    @Autowired
    MemberService memberService;

    @GetMapping(value = "/")
    public String index(Model model) {
        return "index";
    }


    @GetMapping(value = "/games")
    public String games(Model model){
        return "games";
    }
    @PostMapping(value = "/games")
    public String games2(Model model){
        return "games";
    }

//    @RequestMapping(value = "/games", method = {
//            RequestMethod.GET, RequestMethod.POST})
//    public String games(Model model) {
//        return "games";
//    }

}
