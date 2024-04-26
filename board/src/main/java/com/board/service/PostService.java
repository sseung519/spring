package com.board.service;

import com.board.dto.PostFormDto;
import com.board.dto.PostSearchDto;
import com.board.entity.Post;
import com.board.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;

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
}
