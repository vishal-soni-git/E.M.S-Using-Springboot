package com.ems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ems.entities.Posts;
import com.ems.repository.PostsRepository;

@Service
public class PostsService {

    @Autowired
    PostsRepository postsRepository;

    public void savePost(Posts posts){

        postsRepository.save(posts);

    }

    public List<Posts> getAllEmployees(){
        return postsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

}
