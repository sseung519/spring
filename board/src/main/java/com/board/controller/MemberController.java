package com.board.controller;

import com.board.dto.MemberFormDto;
import com.board.entity.Member;
import com.board.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;


    //문의하기
    @GetMapping(value = "/members/qa") //localhost/members/qa
    public String qa() {
        return "member/qa";
    }
    //로그인 화면
    @GetMapping(value = "/members/login") // localhost/members/login
    public String loginMember() {
        return "member/memberLoginForm";
    }
    //회원가입 화면
    @GetMapping(value = "/members/new") // localhost/members/new
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    //회원가입 처리
    @PostMapping(value = "/members/new")
    public String memberForm(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model) {
        //@Valid: 유혀성을 검증하려는 객체 앞에 붙인다.
        //BindingResult: 유혀성 검증 후의 결과가 들어있다.

        //유효성 검증 에러 발생 시, 회원가입 페이지로 이동시킴.
        if(bindingResult.hasErrors()) return "member/memberForm";

        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    //로그인 실패 시
    @GetMapping(value = "/members/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/memberLoginForm";
    }

//    //로그아웃 화면
//    @GetMapping(value = "/members/logout") // localhost/members/logout
//    public String logoutMember() {
//        return "member/memberLogoutForm";
//    }
}
