package com.example.game.service;

import com.example.game.dao.GamesDao;
import com.example.game.dto.Games;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamesServiceImpl implements GamesService{
    @Autowired
    GamesDao gamesDao;

    @Override
    public List<Games> getAllGames() {
        return gamesDao.getAllGames();
    }

    @Override
    public Games getGameByNo(int gNo) {
        return gamesDao.getGameByNo(gNo);
    }

    @Override
    public boolean purchaseGame(HttpSession session, int gNo, int gPrice) {
        return gamesDao.purchaseGame(session, gNo,gPrice);
    }

    @Override
    public void addToLibrary(HttpSession session, int gNo) {
        gamesDao.addToLibrary(session, gNo);
    }
}
