package com.example.sseungBlog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comm {
    private int commId;
    private String commDate;
    private String commContent;
    private int memberId;
    private int postId;

    private Member member;
    private Post post;
}
