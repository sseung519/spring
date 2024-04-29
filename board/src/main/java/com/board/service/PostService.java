package com.board.service;

import com.board.dto.PostFormDto;
import com.board.dto.PostSearchDto;
import com.board.entity.Member;
import com.board.entity.Post;
import com.board.repository.MemberRepository;
import com.board.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Long savePost(PostFormDto postFormDto) throws Exception {
        Post post = postFormDto.createPost(); //dto -> entity
        postRepository.save(post);

        return post.getId();
    }

    //게시물 가져오기
    @Transactional(readOnly = true)
    public PostFormDto getPostDtl(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);

        //entity => dto
        PostFormDto postFormDto = PostFormDto.of(post);

        return postFormDto;
    }

    //게시물 수정하기
    public Long updatePost(PostFormDto postFormDto) throws Exception {
        Post post = postRepository.findById(postFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        post.updatePost(postFormDto);

        return post.getId();
    }
    public Page<Post> getPostPage(PostSearchDto postSearchDto, Pageable pageable) {
        Page<Post> postPage = postRepository.getPostPage(postSearchDto,pageable);
        return postPage;
    }

    //본인확인
    public boolean validatePost(Long postId, String email) {
        //로그인한 사용자
        Member loginmember = memberRepository.findByEmail(email);

        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        String postMember = post.getCreatedBy();

        //로그인한 사용자의 이메일과 주문한 사용자의 이메일이 같은지 비교
        if(!StringUtils.equals(loginmember.getEmail(), postMember)) {
            return false;
        }
        return true;
    }

    //게시물 삭제하기
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        postRepository.delete(post);
    }
}
