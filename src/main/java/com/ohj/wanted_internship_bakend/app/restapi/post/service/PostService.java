package com.ohj.wanted_internship_bakend.app.restapi.post.service;

import com.ohj.wanted_internship_bakend.app.restapi.post.domain.Post;
import com.ohj.wanted_internship_bakend.app.restapi.post.domain.PostReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Post write(PostReq postReq);

    Post put(PostReq postReq);

    Post delete(PostReq postReq);

    Post getPostDetail(Long postId);

    long getCount();

    Page<Post> getPostList(Pageable pageable);
}
