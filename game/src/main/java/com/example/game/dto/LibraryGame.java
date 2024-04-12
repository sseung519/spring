package com.example.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryGame {
    private int lgNo;
    private Games games;
    private Library library;
    private Member member;
}
