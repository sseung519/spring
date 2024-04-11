package com.example.game.service;

import jakarta.servlet.http.HttpSession;

public interface PurchaseService {
    public boolean purchaseGame(HttpSession httpSession, int gNo, int gPrice);
    public void addToLibrary(HttpSession httpSession, int gNo);

}
