package com.example.game.service;

import jakarta.servlet.http.HttpSession;

public interface PurchaseService {
    public boolean purchaseGame(HttpSession session, int gNo, int gPrice);

}
