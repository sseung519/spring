package com.sseungteam.repository;

import com.sseungteam.entity.Library;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    //현재 로그인한 계정의 라이브러리 조회
    @Query("select l from Library l where l.member.email = :email order by l.regTime desc")
    List<Library> findLibrary(@Param("email") String email, Pageable pageable);

    //현재 로그인한 계정의 게임 개수 조회

    @Query("select count(l) from Library l where l.member.email = :email")
    Long countLibrary(@Param("email") String email);

    @Query("SELECT lg.game.id FROM Library l JOIN l.libraryGames lg WHERE l.member.id = :memberId")
    List<Long> findGameIdsByMemberId(@Param("memberId") Long memberId);

}
