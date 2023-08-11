package com.ohj.wanted_internship_bakend.app.restapi.post.service;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.repository.MemberRepository;
import com.ohj.wanted_internship_bakend.app.restapi.post.domain.Post;
import com.ohj.wanted_internship_bakend.app.restapi.post.domain.PostReq;
import com.ohj.wanted_internship_bakend.app.restapi.post.repository.PostRepository;
import com.ohj.wanted_internship_bakend.app.util.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Post put(PostReq postReq) {
        long id = Long.parseLong(JwtManager.getId());
        Member member = memberRepository.findById(id).get();
        Post post = postRepository.findById(postReq.getId()).get();
        post.updateInfo(postReq);

        postRepository.save(post);

        return post;
    }

    @Override
    public Post delete(PostReq postReq) {
        long id = Long.parseLong(JwtManager.getId());
        Post post = postRepository.findById(postReq.getId()).get();

        postRepository.deleteById(postReq.getId());
        return null;
    }

    @Override
    public Post getPostDetail(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    public long getCount() {
        return postRepository.count();
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
