package com.ohj.wanted_internship_bakend.app.restapi.post.domain;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Post {
    @ManyToOne
    private Member author;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String subject;

    private String content;
}
