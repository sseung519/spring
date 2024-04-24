package com.board.dto;

import com.board.constant.PostCategoryStatus;
import com.board.entity.Post;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class PostFormDto {
  private Long id;

  @NotEmpty(message = "제목은 필수 입력값입니다.")
  private String title;

  @NotEmpty(message = "내용은 필수 입력값입니다.")
  private String content;

  private PostCategoryStatus postCategoryStatus;


  private static ModelMapper modelMapper = new ModelMapper();

  //dto -> entity
  public Post createPost() {
      return modelMapper.map(this, Post.class);
  }

  //entity -> dto
  public static PostFormDto of(Post post) {
     return modelMapper.map(post, PostFormDto.class);
   }
}
