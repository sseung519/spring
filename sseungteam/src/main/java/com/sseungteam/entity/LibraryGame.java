package com.sseungteam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name= "library_game")
@Getter
@Setter
@ToString
public class LibraryGame extends BaseEntity{
    @Id
    @Column(name= "library_game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="library_id")
    private Library library;

    public static LibraryGame createLibraryGame(Game game) {
        LibraryGame libraryGame = new LibraryGame();
        libraryGame.setGame(game);

        return libraryGame;
    }



}
