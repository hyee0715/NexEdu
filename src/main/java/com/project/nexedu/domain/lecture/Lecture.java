package com.project.nexedu.domain.lecture;

import com.project.nexedu.domain.Time;
import com.project.nexedu.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Lecture extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private long runningTime;

    @Builder
    public Lecture(Long id, String title, User instructor, String description, long runningTime) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.runningTime = runningTime;
    }

    public void update(String title, String description, long runningTime) {
        this.title = title;
        this.description = description;
        this.runningTime = runningTime;
    }
}
