package com.sseungteam.controller;

import com.sseungteam.dto.GameFormDto;
import com.sseungteam.dto.GameListDto;
import com.sseungteam.dto.LibraryGameDto;
import com.sseungteam.dto.MemberFormDto;
import com.sseungteam.entity.Member;
import com.sseungteam.service.GameImgService;
import com.sseungteam.service.GameService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final MemberService memberService;
    private final LibraryService libraryService;

    //게임등록페이지
    @GetMapping(value = "/game/new")
    public String gameForm(Model model, Principal principal) {
        //헤더 잔고 이름
        Member member = null;
        if(principal != null){
            String email = principal.getName();

            member =  memberService.getMember(email);
        }
        model.addAttribute("member", member);
        model.addAttribute("gameFormDto", new GameFormDto());
        return "game/gameForm";
    }

    //게임등록 인서트
    @PostMapping(value = "/game/new")
    public String gameNew(@Valid GameFormDto gameFormDto, BindingResult bindingResult, Model model, Principal principal,
                          @RequestParam("gameImgFile")List<MultipartFile> gameimgFileList) {
        //헤더 잔고 이름
        Member member = null;
        if(principal != null){
            String email = principal.getName();

            member =  memberService.getMember(email);
        }
        model.addAttribute("member", member);

        if(bindingResult.hasErrors()) return "game/gameForm";

        if(gameimgFileList.get(0).isEmpty()) {
            model.addAttribute("errormessage",
                    "첫번째 상품 이미지는 필수 입력입니다.");
        }
        try {
            gameService.saveGame(gameFormDto, gameimgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "게임등록 중 에러가 발생헀습니다.");
            return "game/gameForm";
        }
        return "redirect:/";
    }

    //게임수정 페이지
    @GetMapping(value = "/game/modify/{gameId}")
    public String gameModifyform(@PathVariable("gameId") Long gameId, Model model, Principal principal) {
        //헤더 잔고 이름
        Member member = null;
        if(principal != null){
            String email = principal.getName();

            member =  memberService.getMember(email);
        }
        model.addAttribute("member", member);

        try {
            GameFormDto gameFormDto = gameService.getGameDtl(gameId);
            model.addAttribute("gameFormDto", gameFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "게임정보를 가져오는 도중 에러가 발생했습니다.");

            //에러 발생 시 dto 넘겨준다
            model.addAttribute("gameFormDto", new GameFormDto());
            return "game/gameForm";
        }
        return "game/gameModifyForm";
    }

    //게임 수정(update)
    @PostMapping(value = "/game/modify/{gameId}")
    public String gameUpdate(@Valid GameFormDto gameFormDto, Model model, BindingResult bindingResult,
                             @RequestParam("gameImgFile") List<MultipartFile> gameImgFileList,
                             @PathVariable("gameId") Long gameId) {
        if(bindingResult.hasErrors()) return "game/gameForm";

        if(gameImgFileList.get(0).isEmpty() && gameFormDto.getId() == null) {
            model.addAttribute("errorMessage",
                    "첫번째 게임 이미지는 필수로 등록해주세요.");
            return "game/gameModifyForm";
        }

        try {
            gameService.updateGame(gameFormDto, gameImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "게임 수정 중 에러가 발생했습니다.");
        }
        return "redirect:/game/detail/{gameId}";
    }

    //게임리스트
    @GetMapping(value ={"/game", "/game/{page}"})
    public String gameList(Model model, Principal principal,
                           @PathVariable("page")Optional<Integer> page) {
        //헤더 잔고 이름
        Member member = null;
        if(principal != null){
            String email = principal.getName();

            member =  memberService.getMember(email);
        }
        model.addAttribute("member", member);



        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<GameListDto> games = gameService.getGameListPage(pageable);
        model.addAttribute("games", games);
        model.addAttribute("maxPage", 5);

        return "game/gameList";
    }

    //게임 디테일
    @GetMapping(value = "/game/detail/{gameId}")
    public String gameDetail(Model model, Principal principal,
                             @PathVariable(value = "gameId") Long gameId) {
        Member member = null;
        if(principal != null){
            String email = principal.getName();
            member = memberService.getMember(email);
        }
        model.addAttribute("member", member);

        GameFormDto gameFormDto = gameService.getGameDtl(gameId);
        model.addAttribute("game", gameFormDto);

        // 사용자의 라이브러리에 있는 게임 목록 가져오기
        List<Long> libraryGameIds = null;
        if (member != null) {
            libraryGameIds = libraryService.getGameIdsInLibrary(member.getId());
        }
        model.addAttribute("libraryGameIds", libraryGameIds);

        return "game/gameDetail";
    }

    //게임디테일
//    @GetMapping(value = "/game/detail/{gameId}")
//    public String gameDetail(Model model, Principal principal,
//                             @PathVariable(value = "gameId") Long gameId) {
//        //헤더 잔고 이름
//        Member member = null;
//        if(principal != null){
//            String email = principal.getName();
//
//            member =  memberService.getMember(email);
//        }
//        model.addAttribute("member", member);
//        GameFormDto gameFormDto = gameService.getGameDtl(gameId);
//        model.addAttribute("game", gameFormDto);
//
//
//        return "game/gameDetail";
//    }

    //게임 삭제

    @DeleteMapping(value = "/game/delete/{gameId}")
    public @ResponseBody ResponseEntity deleteGame(@PathVariable("gameId") Long gameId) {
        gameService.deleteGame(gameId);
        return new ResponseEntity<Long>(gameId, HttpStatus.OK);
    }
}
