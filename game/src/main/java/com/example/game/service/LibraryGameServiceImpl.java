package com.example.game.service;

import com.example.game.dao.LibraryGameDao;
import com.example.game.dto.Games;
import com.example.game.dto.Library;
import com.example.game.dto.LibraryGame;
import com.example.game.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryGameServiceImpl implements LibraryGameService{
    @Autowired
    LibraryGameDao libraryGameDao;


}
