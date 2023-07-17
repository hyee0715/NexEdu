package com.project.nexedu.domain.board;

import com.project.nexedu.domain.Time;
import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Board extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Builder
    public Board(Long id, String title, User writer, String content, Lecture lecture) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.lecture = lecture;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
