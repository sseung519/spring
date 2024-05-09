package com.sseungteam.dto;

import com.sseungteam.entity.GameImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class GameImgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    //엔티티 > dto 하기 위해 모델매퍼
    public static GameImgDto of(GameImg gameImg) {
        return modelMapper.map(gameImg, GameImgDto.class);
    }
}
