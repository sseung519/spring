package com.sseungteam.service;

import com.sseungteam.dto.LibraryDto;
import com.sseungteam.dto.LibraryGameDto;
import com.sseungteam.dto.LibraryListDto;
import com.sseungteam.entity.*;
import com.sseungteam.repository.GameImgRepository;
import com.sseungteam.repository.GameRepository;
import com.sseungteam.repository.LibraryRepository;
import com.sseungteam.repository.MemberRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryService {
    private final GameRepository gameRepository;
    private final GameImgRepository gameImgRepository;
    private final MemberRepository memberRepository;
    private final LibraryRepository libraryRepository;

    public Long purchaseAndUpdateBalance(LibraryDto libraryDto, String email, int price) throws Exception {
        Member member = memberRepository.findByEmail(email);

        // 잔고 확인 후 구매 가능 여부 결정
        if (member.getBalance() >= price) {
            Game game = gameRepository.findById(libraryDto.getGameId())
                    .orElseThrow(EntityExistsException::new);

            List<LibraryGame> libraryGameList = new ArrayList<>();
            LibraryGame libraryGame = LibraryGame.createLibraryGame(game);
            libraryGameList.add(libraryGame);

            Library library = Library.createLibrary(member, libraryGameList);

            // 구매 후 잔고 차감
            member.setBalance(member.getBalance() - price);
            memberRepository.save(member);

            // 구매 진행
            libraryRepository.save(library);

            return library.getId();
        } else {
            return -1L;
        }
    }

//    //구매하기
//    public Long purchase(LibraryDto libraryDto, String email) {
//        //1. 주문한 게임의 game 객체를 가져온다.
//        Game game = gameRepository.findById(libraryDto.getGameId())
//                .orElseThrow(EntityExistsException::new);
//
//        //2. 현재 로그인한 계정의 이메일을 이용해 memeber 엔티티를 가져온다.
//        Member member = memberRepository.findByEmail(email);
//
//        //양방향 객체의 save
//        List<LibraryGame> libraryGameList = new ArrayList<>();
//        LibraryGame libraryGame = LibraryGame.createLibraryGame(game);
//        libraryGameList.add(libraryGame);
//
//        Library library = Library.createLibrary(member, libraryGameList);
//
//
//        libraryRepository.save(library); //insert
//
//        return library.getId();
//    }
//    //구매 후 잔고 차감
//    public void updateBalance(String email, int price) {
//        Member member = memberRepository.findByEmail(email);
//
//        if(member.getBalance() - price < 0) {
//            System.out.println("잔고가 부족하여 구매를 실패하였습니다.");
//        } else {
//            member.setBalance(member.getBalance() - price);
//            memberRepository.save(member);
//        }
//
//    }

    //게임목록
    @Transactional(readOnly = true)
    public Page<LibraryListDto> getLibraryList(String email, Pageable pageable) {
        List<Library> libraries = libraryRepository.findLibrary(email, pageable);

        Long totalCount = libraryRepository.countLibrary(email);

        List<LibraryListDto> libraryListDtos = new ArrayList<>();

        for(Library library: libraries) {
            LibraryListDto libraryListDto = new LibraryListDto(library);

            List<LibraryGame> libraryGames = library.getLibraryGames();

            for(LibraryGame libraryGame : libraryGames) {
                GameImg gameImg = gameImgRepository
                        .findByGameIdAndRepImgYn(libraryGame.getGame().getId(), "Y");

                LibraryGameDto libraryGameDto =
                        new LibraryGameDto(libraryGame, gameImg.getImgUrl());
                libraryListDto.addLibraryGameDto(libraryGameDto);
            }

            libraryListDtos.add(libraryListDto); //주문내역
        }

        return new PageImpl<>(libraryListDtos, pageable, totalCount);
    }

    //본인확인
//    @Transactional(readOnly = true)
//    public boolean validatePurchase(Long libraryId, String email) {
//        //로그인한 사용자
//        Member curMember = memberRepository.findByEmail(email);
//        //라이브러리내역
//        Library library = libraryRepository.findById(libraryId)
//                .orElseThrow(EntityNotFoundException::new);
//
//        Member savedMember = library.getMember(); //구매한 사용자 찾기
//
//        //로그인한 계정의 이메일과 주문한 계정의 이메일이 같은지 비교
//        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
//            return false;
//        }
//        return true;
//    }

    //라이브러리 게임 리스트가져오기
    public List<Long> getGameIdsInLibrary(Long memberId) {
        return libraryRepository.findGameIdsByMemberId(memberId);
    }
}
