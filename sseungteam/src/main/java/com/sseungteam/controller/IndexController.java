package com.sseungteam.controller;

import com.sseungteam.dto.MemberFormDto;
import com.sseungteam.entity.Member;
import com.sseungteam.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final MemberService memberService;
    @GetMapping(value = "/")
    public String index(Model model, Principal principal) {
        Member member = null;
        if(principal != null){
            String email = principal.getName();

            member =  memberService.getMember(email);
        }
        model.addAttribute("member", member);

        return "index";
    }
}
