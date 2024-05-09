package com.sseungteam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "library")
@Getter
@Setter
@ToString
public class Library extends BaseEntity{
    @Id
    @Column(name= "library_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id") //FK키
    private Member member;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.LAZY
    , orphanRemoval = true)
    private List<LibraryGame> libraryGames = new ArrayList<>();

    public void addLibraryGame(LibraryGame libraryGame) {
        libraryGames.add(libraryGame);
        libraryGame.setLibrary(this);
    }

    public static Library createLibrary(Member member, List<LibraryGame> libraryGameList) {
        Library library = new Library();
        library.setMember(member);

        for(LibraryGame libraryGame : libraryGameList) {
            library.addLibraryGame(libraryGame);
        }

        return library;
    }




}
