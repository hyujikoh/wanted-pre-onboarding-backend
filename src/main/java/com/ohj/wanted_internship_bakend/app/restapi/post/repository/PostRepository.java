package com.ohj.wanted_internship_bakend.app.restapi.post.repository;

import com.ohj.wanted_internship_bakend.app.restapi.post.domain.Post;

public interface PostRepository {
    void save(Post post);
}
