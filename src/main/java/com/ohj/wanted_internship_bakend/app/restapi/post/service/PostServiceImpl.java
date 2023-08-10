package com.ohj.wanted_internship_bakend.app.restapi.post.service;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.repository.MemberRepository;
import com.ohj.wanted_internship_bakend.app.restapi.post.domain.Post;
import com.ohj.wanted_internship_bakend.app.restapi.post.domain.PostReq;
import com.ohj.wanted_internship_bakend.app.restapi.post.repository.PostRepository;
import com.ohj.wanted_internship_bakend.app.util.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public Post write(PostReq postReq) {
        long id = Long.parseLong(JwtManager.getId());
        Member member = memberRepository.findById(id).get();

        Post post = postBuilder(postReq, member);
        postRepository.save(post);

        return post;
    }

    private Post postBuilder(PostReq postReq, Member member) {
        Post post = new Post().builder()
                .author(member)
                .subject(postReq.getSubject())
                .content(postReq.getContent())
                .build();
        return post;
    }
}
