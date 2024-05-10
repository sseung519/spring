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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public static LibraryGame createLibraryGame(Game game, Member member) {
        LibraryGame libraryGame = new LibraryGame();
        libraryGame.setGame(game);
        libraryGame.setMember(member);

        return libraryGame;
    }


}
