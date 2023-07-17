package com.project.nexedu.domain.board;

import com.project.nexedu.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByLecture(Lecture lecture);
}
