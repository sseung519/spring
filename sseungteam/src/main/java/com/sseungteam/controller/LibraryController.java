package com.sseungteam.controller;

import com.sseungteam.dto.LibraryDto;
import com.sseungteam.dto.LibraryGameDto;
import com.sseungteam.dto.LibraryListDto;
import com.sseungteam.dto.MemberFormDto;
import com.sseungteam.entity.Member;
import com.sseungteam.service.LibraryService;
import com.sseungteam.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;
    private final MemberService memberService;

    @PostMapping(value = "/purchase")
    public @ResponseBody ResponseEntity purchase(
            @RequestBody @Valid LibraryDto libraryDto,
            BindingResult bindingResult, Principal principal,
            Model model) {
        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();

            //유효성 체크 후 에러 결과
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();

        try {
            Long libraryId = libraryService.purchaseAndUpdateBalance(libraryDto, email, libraryDto.getPrice());
            model.addAttribute("member", memberService.getMember(email));
            model.addAttribute("memberFormDto", new MemberFormDto());
            return new ResponseEntity<Long>(libraryId, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //원본
//    @PostMapping(value = "/purchase")
//    public @ResponseBody ResponseEntity purchase(
//            @RequestBody @Valid LibraryDto libraryDto,
//            BindingResult bindingResult, Principal principal,
//            Model model) {
//        if(bindingResult.hasErrors()) {
//            StringBuilder sb = new StringBuilder();
//
//            //유효성 체크 후 에러 결과
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//
//            for (FieldError fieldError : fieldErrors) {
//                sb.append(fieldError.getDefaultMessage());
//            }
//            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
//        }
//        libraryService.updateBalance(principal.getName(), libraryDto.getPrice());
//        model.addAttribute("member", memberService.getMember(principal.getName()));
//        model.addAttribute("memberFormDto", new MemberFormDto());
//
//        String email = principal.getName();
//        Long libraryId;
//
//        try {
//            libraryId = libraryService.purchase(libraryDto, email);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<Long>(libraryId, HttpStatus.OK);
//    }

    //라이브러리 페이지
    @GetMapping(value = {"/library", "/library/{page}"})
    public String library(@PathVariable(value = "page")Optional<Integer> page,
                          Principal principal, Model model) {
        //헤더 잔고 이름
        Member member = null;
        if(principal != null){
            String email = principal.getName();

            member =  memberService.getMember(email);
        }
        model.addAttribute("member", member);

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 15);

        Page<LibraryListDto> libraryListDtoList =
                libraryService.getLibraryList(principal.getName(), pageable);

        model.addAttribute("libraries", libraryListDtoList);
        model.addAttribute("maxPage", 5);

        return "library/library";
    }
}
