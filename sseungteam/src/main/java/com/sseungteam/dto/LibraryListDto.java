package com.sseungteam.dto;

import com.sseungteam.entity.Library;
import com.sseungteam.entity.LibraryGame;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LibraryListDto {
    public LibraryListDto(Library library) {
        this.libraryId = library.getId();
    }

    private Long libraryId;

    private List<LibraryGameDto> libraryGameDtoList = new ArrayList<>();

    public void addLibraryGameDto(LibraryGameDto libraryGameDto) {
        this.libraryGameDtoList.add(libraryGameDto);
    }
}

