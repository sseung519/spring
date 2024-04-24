package com.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name="comment")
@Getter
@Setter
@ToString
public class Comment extends BaseEntity {
    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime regDate;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

}
