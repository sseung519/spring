package com.example.game.dao;

import com.example.game.dto.Library;
import com.example.game.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LibraryDao {
    public void addToLibrary(Library library, Member member);
}
