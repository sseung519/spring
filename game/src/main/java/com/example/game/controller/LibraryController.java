package com.example.game.controller;

import com.example.game.dto.Games;
import com.example.game.dto.Library;
import com.example.game.dto.LibraryGame;
import com.example.game.dto.Member;
import com.example.game.service.LibraryGameService;
import com.example.game.service.LibraryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LibraryController {
    @Autowired
    LibraryService libraryService;
    @Autowired
    LibraryGameService libraryGameService;


}

