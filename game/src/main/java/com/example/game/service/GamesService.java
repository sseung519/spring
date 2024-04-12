package com.example.game.service;

import com.example.game.dto.Games;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface GamesService {
   public List<Games> getAllGames();
   public Games getGameByNo(int gNo);
    public boolean purchaseGame(HttpSession session, int gNo, int gPrice);
    public void addToLibrary(HttpSession session, int gNo);
}
