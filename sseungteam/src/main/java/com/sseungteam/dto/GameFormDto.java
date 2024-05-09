package com.sseungteam.dto;

import com.sseungteam.constant.GameRelease;
import com.sseungteam.entity.Game;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameFormDto {
    private Long id;

    @NotBlank(message = "게임명은 필수 입력 값입니다.")
    private String gameName;

    @NotBlank(message = "게임 상세설명은 필수 입력 값입니다.")
    private String gameDetail;

    private String gameDate;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private int price;

    private GameRelease gameRelease;

    //게임 이미지 저장
    private List<GameImgDto> gameImgDtoList = new ArrayList<>();

    //상품 이미지 아이디 저장
    private List<Long> gameImgIds =  new ArrayList<>();

    //modelMapper
    private static ModelMapper modelMapper = new ModelMapper();

    //entity -> dto
    public static GameFormDto of(Game game) {
        return modelMapper.map(game, GameFormDto.class);
    }

    //dto -> entity
    public Game createGame() { //this는 GameFormDto
        return modelMapper.map(this, Game.class); //entity 객체 리턴
    }
}
