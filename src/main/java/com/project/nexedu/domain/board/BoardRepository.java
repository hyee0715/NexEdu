package com.project.nexedu.domain.board;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByLecture(Lecture lecture);
    List<Board> findByWriter(User writer);

    void deleteByLecture(Lecture lecture);
    void deleteByWriter(User writer);
}
