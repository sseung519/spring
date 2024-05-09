package com.sseungteam.repository;

import com.sseungteam.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GameRepository extends JpaRepository<Game, Long>,
        QuerydslPredicateExecutor<Game>, GameRepositoryCustom {
}
