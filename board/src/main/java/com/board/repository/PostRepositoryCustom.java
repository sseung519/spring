package com.board.repository;

import com.board.dto.PostSearchDto;
import com.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> getPostPage(PostSearchDto postSearchDto, Pageable pageable);
}
