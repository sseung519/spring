package com.board.entity;

import com.board.constant.PostCategoryStatus;
import com.board.dto.PostFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name="post")
@Getter
@Setter
@ToString
public class Post extends BaseEntity{
    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    @Column(columnDefinition = "longtext")
    private String content;

    @Enumerated(EnumType.STRING)
    private PostCategoryStatus postCategoryStatus;


    public void updatePost(PostFormDto postFormDto) {
        this.title = postFormDto.getTitle();
        this.content = postFormDto.getContent();
        this.postCategoryStatus = postFormDto.getPostCategoryStatus();
    }
}
