package com.shopmax.dto;

import com.shopmax.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {
    private Long id;

    private String imgName; //UUID로 바뀐 이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 경로

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    //entity -> dto
    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class); //ItemImgDto 객체 리턴
    }
}
