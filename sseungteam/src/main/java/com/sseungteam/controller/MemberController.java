package com.sseungteam.controller;

import com.sseungteam.dto.MemberFormDto;
import com.sseungteam.entity.Member;
import com.sseungteam.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    //로그인
    @GetMapping(value = "/member/login")
    public String loginMember(Model model, Principal principal) {
        model.addAttribute("member", new MemberFormDto());
        return "member/login";
    }

    //회원가입
    @GetMapping(value = "/member/register")
    public String registerPage(Model model ,Principal principal) {
        //헤더 잔고 이름
        Member member = null;
        if(principal != null){
            String email = principal.getName();

            member =  memberService.getMember(email);
        }
        model.addAttribute("member", member);
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/register";
    }

    //회원가입 처리
    @PostMapping(value = "/member/new")
    public String registerMember(@Valid MemberFormDto memberFormDto,
                                 BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) return "member/register";

        try {
            Member member = Member.createMember(memberFormDto ,passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/register";
        }

        return "redirect:/";
    }


    //로그인 실패 시
    @GetMapping(value = "/member/login/error")
    public String loginError(Model model){
        //유효성 체크를 위해서 memberFormDto 객체를 매핑하기 위해 전달
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요.");
        return "member/login"; //로그인 페이지로 그대로 이동
    }

    //잔고 페이지
    @GetMapping(value ="/member/charge")
    public String chargePage(Model model, Principal principal) {
        model.addAttribute("member", memberService.getMember(principal.getName()));
        return "member/charge";
    }

    //잔고 충전
    @PostMapping(value = "/member/charge")
    public String chargeBalance(Model model, Principal principal, @RequestParam("charge") int charge) {
        memberService.chargeBalance(principal.getName(), charge);
        model.addAttribute("member", memberService.getMember(principal.getName()));
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/charge";
    }
}
