package com.sseungteam.dto;

import com.sseungteam.entity.LibraryGame;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryGameDto {
    public LibraryGameDto(LibraryGame libraryGame, String imgUrl) {
        this.gameName = libraryGame.getGame().getGameName();
        this.price = libraryGame.getGame().getPrice();
        this.imgUrl = imgUrl;
        this.gameId = libraryGame.getGame().getId();
        this.memberId = libraryGame.getLibrary().getMember().getId();
    }

    private Long gameId;
    private String gameName;
    private int price;
    private String imgUrl;
    private Long memberId;
}
