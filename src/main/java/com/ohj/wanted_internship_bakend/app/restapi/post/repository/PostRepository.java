package com.ohj.wanted_internship_bakend.app.restapi.post.repository;

import com.ohj.wanted_internship_bakend.app.restapi.post.domain.Post;

import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(long id);

    void deleteById(long id);

    long count();
}
