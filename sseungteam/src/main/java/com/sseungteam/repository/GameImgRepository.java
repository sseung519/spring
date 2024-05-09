package com.sseungteam.repository;

import com.sseungteam.entity.GameImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameImgRepository extends JpaRepository<GameImg, Long> {
    List<GameImg> findByGameIdOrderByIdAsc(Long gameId);

    GameImg findByGameIdAndRepImgYn(Long gameId, String repImgYn);
}
