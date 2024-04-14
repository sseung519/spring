package com.example.sseungBlog.dao;

import com.example.sseungBlog.dto.Comm;
import com.example.sseungBlog.dto.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostDao {
    public List<Post> getPostList(Map map) throws Exception;
    public List<Post> getNoticeList(Map map) throws Exception;
    public int getNoticeDataCount(Map map) throws Exception;
    public int getDataCount(Map map) throws Exception;

    public Post getReadPost(int postId) throws Exception;
    public void updateHitCount(int postId) throws Exception;

    public void insertPost(Post post) throws Exception;

    public void updatePost(Post post) throws Exception;
    public void deletePost(int postId) throws Exception;

    public void insertComm(Comm comm) throws Exception;
}