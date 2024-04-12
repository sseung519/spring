package com.example.game.service;

import com.example.game.dto.Games;
import com.example.game.dto.Library;
import com.example.game.dto.LibraryGame;
import com.example.game.dto.Member;

public interface LibraryService {
    public void addToLibrary(Library library, Member member);

}
