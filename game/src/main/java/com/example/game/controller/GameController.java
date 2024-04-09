package com.example.game.controller;

import com.example.game.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {
    @Autowired
    MemberService memberService;

    @GetMapping(value = "/")
    public String index(Model model) {
        return "index";
    }


}
