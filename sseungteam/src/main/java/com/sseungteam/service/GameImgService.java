package com.sseungteam.service;

import com.sseungteam.entity.GameImg;
import com.sseungteam.repository.GameImgRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class GameImgService {
    @Value("/Users/sseung/Desktop/project/Temp/sseungteam/game")
    private String gameImgLocation;

    private final GameImgRepository gameImgRepository;
    private final FileService fileService;

    public void saveGameImg(GameImg gameImg, MultipartFile gameImgFile) throws Exception {
        String oriImgName = gameImgFile.getOriginalFilename(); //파일이름
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(gameImgLocation,
                    oriImgName, gameImgFile.getBytes());

            imgUrl = "/images/game/" + imgName;
        }

        //DB에 insert하기 전 유저가 직접 입력하지 못하는 값들 개발자가 입력
        gameImg.updateGameImg(oriImgName, imgName, imgUrl);
        gameImgRepository.save(gameImg); //insert
    }

    //이미지 수정
    public void updateGameImg(Long gameImgId, MultipartFile gameImgFile) throws Exception {
        if(!gameImgFile.isEmpty()) {
            GameImg savedGameImg = gameImgRepository.findById(gameImgId)
                    .orElseThrow(EntityExistsException::new);

            if(!StringUtils.isEmpty(savedGameImg.getImgName())) {
                fileService.deleteFile(gameImgLocation + "/" + savedGameImg.getImgName());
            }

            String oriImgName = gameImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(gameImgLocation, oriImgName,
                    gameImgFile.getBytes());
            String imgUrl = "/images/game/" + imgName;
            //update(jpa가 자동감지)
            savedGameImg.updateGameImg(oriImgName, imgName, imgUrl);
        }
    }
}
