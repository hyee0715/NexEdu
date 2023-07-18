package com.project.nexedu.domain.comment;

import com.project.nexedu.domain.Time;
import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @Builder
    public Comment(Long id, String content, Board board, User writer) {
        this.id = id;
        this.content = content;
        this.board = board;
        this.writer = writer;
    }

    public void update(String content) {
        this.content = content;
    }
}
