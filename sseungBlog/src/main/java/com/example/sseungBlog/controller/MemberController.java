package com.example.sseungBlog.controller;

import com.example.sseungBlog.dto.Member;
import com.example.sseungBlog.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @Autowired
    MemberService memberService;

    @GetMapping(value="/login")
    public String login(){ return "member/login";}

    @PostMapping(value="/login")
    public String loginMember(Member member, HttpSession session){
        try {
            Member loginMember = memberService.loginMember(member);

            if(loginMember != null){
                session.setAttribute("name", loginMember.getName());
                session.setAttribute("member_id", loginMember.getMemberId());
                session.setAttribute("admin", loginMember.getAdmin());

                return "redirect:/";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "member/login";
    }

    @GetMapping(value="/logout")
    public String logoutMember(HttpSession session) {
        //세션에 저장된 name과 member_id 삭제
        session.removeAttribute("name");
        session.removeAttribute("member_id");
        session.removeAttribute("admin");

        return "redirect:/"; //로그아웃 성공 시, index 페이지로 redirect
    }

    @GetMapping(value="/register")
    public String register(){return "member/register";}

    @PostMapping(value="/register")
    public String registerMember(Member member){
        try {
             memberService.registerMember(member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/";
    }
}
