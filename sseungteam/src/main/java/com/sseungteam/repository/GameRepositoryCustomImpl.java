package com.sseungteam.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sseungteam.dto.GameListDto;
import com.sseungteam.dto.QGameListDto;
import com.sseungteam.entity.QGame;
import com.sseungteam.entity.QGameImg;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class GameRepositoryCustomImpl implements GameRepositoryCustom{
    private JPAQueryFactory queryFactory;
    public GameRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<GameListDto> getGameListPage(Pageable pageable) {
        QGame game = QGame.game;
        QGameImg gameImg = QGameImg.gameImg;

        List<GameListDto> content = queryFactory
                .select(
                        new QGameListDto(
                                game.id,
                                game.gameName,
                                game.gameDetail,
                                gameImg.imgUrl,
                                game.price,
                                game.gameRelease)
                )
                .from(gameImg)
                .join(gameImg.game, game)
                .where(gameImg.repImgYn.eq("Y"))
                .orderBy(game.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(gameImg)
                .join(gameImg.game, game)
                .where(gameImg.repImgYn.eq("Y"))
                .fetchOne();


        return new PageImpl<>(content, pageable, total);
    }
}
