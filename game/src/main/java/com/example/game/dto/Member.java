package com.example.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private int memberId;
    private String email;
    private String password;
    private String name;
    private int balance;

}
