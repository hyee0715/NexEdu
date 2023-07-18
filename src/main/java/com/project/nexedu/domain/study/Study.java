package com.project.nexedu.domain.study;

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
public class Study extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Study(Long id, Lecture lecture, User user) {
        this.id = id;
        this.lecture = lecture;
        this.user = user;
    }

    public void update(Lecture lecture, User user) {
        this.lecture = lecture;
        this.user = user;
    }
}