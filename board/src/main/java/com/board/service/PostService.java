package com.board.service;

import com.board.dto.PostFormDto;
import com.board.entity.Post;
import com.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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

}
