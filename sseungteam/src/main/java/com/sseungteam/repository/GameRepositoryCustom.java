package com.sseungteam.repository;

import com.sseungteam.dto.GameListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameRepositoryCustom {
    Page<GameListDto> getGameListPage(Pageable pageable);
}
