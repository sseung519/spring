package com.board.controller;

import com.board.dto.PostFormDto;
import com.board.dto.PostSearchDto;
import com.board.entity.Post;
import com.board.service.PostService;
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

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value ="/post/write")
    public String postForm(Model model) {
        model.addAttribute("postFormDto", new PostFormDto());
        return "post/write";
    }

    @PostMapping(value = "/post/new")
    public String postNew(@Valid PostFormDto postFormDto, Model model,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "post/write";

        try {
            postService.savePost(postFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "상품등록 중 에러가 발생했습니다.");
            return "post/write";
        }

        return "redirect:/";
    }

    //게시글 수정페이지
    @GetMapping(value = "/post/{postId}")
    public String postUpdate(@PathVariable("postId") Long postId, Model model) {
       try {
           PostFormDto postFormDto = postService.getPostDtl(postId);
           model.addAttribute("postFormDto", postFormDto);
       } catch (Exception e) {
           e.printStackTrace();
           model.addAttribute("errorMessage",
                   "게시물 정보를 가져오는 도중 에러가 발생했습니다.");

           model.addAttribute("postFormDto", new PostFormDto());
           return "post/write";
       }
       return "post/rewrite";
    }

    //게시글 수정(update문)
    @PostMapping(value = "/post/{postId}")
    public String postUpdate(@Valid PostFormDto postFormDto,
                             Model model, BindingResult bindingResult,
                             @PathVariable("postId") Long postId) {
        if(bindingResult.hasErrors()) return "post/write";
        PostFormDto getPostFormDto = postService.getPostDtl(postId);
        try {
            postService.updatePost(postFormDto);

            } catch(Exception e) {
                e.printStackTrace();
                model.addAttribute("errorMessage",
                        "게시물 수정 중 에러가 발생했습니다.");
                model.addAttribute("postFormDto", getPostFormDto);
        }
        return "redirect:/post/list";
    }

    @GetMapping(value = {"/post/list", "/post/list/{page}"})
    public String postList(Model model, PostSearchDto postSearchDto,
                           @PathVariable(value = "page")Optional<Integer> page){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,5);

        Page<Post> posts = postService.getPostPage(postSearchDto, pageable);
        model.addAttribute("posts", posts);
        model.addAttribute("postSearchDto", postSearchDto);
        model.addAttribute("maxPage", 5);

        return "post/list";
    }


    //포스트 상세 페이지
    @GetMapping(value = "/post/detail/{postId}")
    public String postDtl(Model model, @PathVariable(value= "postId") Long postId,
                          Principal principal){
       PostFormDto postFormDto = postService.getPostDtl(postId);
        model.addAttribute("post", postFormDto);
        model.addAttribute("memberId", principal.getName());
        return "post/detail";
    }

    //포스트 삭제

    @DeleteMapping(value = "/post/delete/{postId}")
    public @ResponseBody ResponseEntity deletePost(@PathVariable(value = "postId") Long postId,
                                                   Principal principal) {
        if(!postService.validatePost(postId, principal.getName())) {
            return new ResponseEntity<String>("포스트 삭제 권한이 없습니다.",
                    HttpStatus.FORBIDDEN);
        }
        postService.deletePost(postId);
        return new ResponseEntity<Long>(postId, HttpStatus.OK);
    }
}
