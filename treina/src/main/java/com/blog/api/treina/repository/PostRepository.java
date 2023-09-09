package com.blog.api.treina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.api.treina.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
