package com.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.entities.Posts;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Long> {

}
