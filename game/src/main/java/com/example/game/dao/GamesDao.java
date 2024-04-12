package com.example.game.dao;

import com.example.game.dto.Games;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GamesDao {
    public List<Games> getAllGames();
    public Games getGameByNo(int gNo);
    public boolean purchaseGame(HttpSession session, int gNo, int gPrice);
    public void addToLibrary(HttpSession session, int gNo);

}
