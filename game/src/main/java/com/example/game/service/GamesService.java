package com.example.game.service;

import com.example.game.dto.Games;

import java.util.List;

public interface GamesService {
   public List<Games> getAllGames();
   public Games getGameByNo(int gNo);
}
