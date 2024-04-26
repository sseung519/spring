package com.board.dto;

import com.board.constant.PostCategoryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchDto {
    private String searchDateType;
    private PostCategoryStatus searchPostCategoryStatus;
    private String searchBy;
    private String searchQuery = "";
}
