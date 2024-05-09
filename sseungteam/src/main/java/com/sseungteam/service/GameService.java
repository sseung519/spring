package com.sseungteam.service;

import com.sseungteam.dto.GameFormDto;
import com.sseungteam.dto.GameImgDto;
import com.sseungteam.dto.GameListDto;
import com.sseungteam.entity.Game;
import com.sseungteam.entity.GameImg;
import com.sseungteam.repository.GameImgRepository;
import com.sseungteam.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {
    private final GameRepository gameRepository;
    private final GameImgRepository gameImgRepository;
    private final GameImgService gameImgService;

    //game 테이블에 게임등록(insert)
    public Long saveGame(GameFormDto gameFormDto,
                         List<MultipartFile> gameImgFileList) throws Exception {
        //1. 게임 등록(insert)
        Game game = gameFormDto.createGame();
        gameRepository.save(game);

        //2. 이미지 등록(n개의 이미지 등록 for문으로 하나씩 저장
        for (int i = 0; i < gameImgFileList.size(); i++) {
            GameImg gameImg = new GameImg();
            gameImg.setGame(game); //gameImg가 game을 참조하므로

            //첫번째 이미지 대표 지정
            if(i == 0) {
                gameImg.setRepImgYn("Y");
            } else {
                gameImg.setRepImgYn("N");
            }

            gameImgService.saveGameImg(gameImg, gameImgFileList.get(i));
        }

        return game.getId(); //등록한 게임의 id 리턴
    }

    //게임 가져오기
    @Transactional(readOnly = true)
    public GameFormDto getGameDtl(Long gameId) {
        //1. game_img 테이블의 이미지를 가져온다.
        List<GameImg> gameImgList = gameImgRepository.findByGameIdOrderByIdAsc(gameId);

        //GameImg Entity > DTO 변환
        List<GameImgDto> gameImgDtoList = new ArrayList<>();
        for (GameImg gameImg : gameImgList) {
            GameImgDto gameImgDto = GameImgDto.of(gameImg); //DTO 객체로 변환
            gameImgDtoList.add(gameImgDto); //dto객체로 바꾼 객체를 리스트에 넣어준다.
        }

        //2. game 테이블에 있는 데이터를 가져온다.
        Game game = gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);

        //Game 엔티티 > dto변환
        GameFormDto gameFormDto = GameFormDto.of(game);

        //3. GameFormDto에 gameImgDtoList를 넣어준다.
        gameFormDto.setGameImgDtoList(gameImgDtoList);

        return gameFormDto;
    }

    //게임 수정하기
    public Long updateGame(GameFormDto gameFormDto,
                           List<MultipartFile> gameImgFileList) throws Exception {
        //1. game 엔티티 수정
        //update를 진행하기전 select를 해온다
        //영속성 컨텍스트에 game 엔티티가 없다면 가져오기 위해
        Game game = gameRepository.findById(gameFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        //update실행
        //* 한번 select를 진행하면 엔티티가 영속성 컨텍스트에 저장되고
        // 변경감지 기능으로 인해 트랜잭션 커밋 시점에 엔티티와 DB에 저장된 값이
        // 다른 내용을 JPA에서 update 해준다.
        game.updateGame(gameFormDto);

        //2. game_img 엔티티 수정
        List<Long> gameImgIds = gameFormDto.getGameImgIds(); //게임이미지 아이디리스트 조회

        //n개의 이미지 파일을 업로드 for문으로 하나씩
        for (int i = 0; i < gameImgFileList.size(); i++) {
            gameImgService.updateGameImg(gameImgIds.get(i), gameImgFileList.get(i));
        }
        return game.getId();
    }

    //게임리스트
    public Page<GameListDto> getGameListPage(Pageable pageable) {
        Page<GameListDto> gameListDtoPage = gameRepository.getGameListPage(pageable);

        return gameListDtoPage;
    }

    //게임 삭제
    public void deleteGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(EntityNotFoundException::new);

        gameRepository.delete(game);
    }
}
