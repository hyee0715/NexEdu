package com.project.nexedu.domain.study;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {

    List<Study> findByUser(User user);
    List<Study> findByLecture(Lecture lecture);
    Optional<Study> findByLectureAndUser(Lecture lecture, User user);

    void deleteByUser(User user);
    void deleteByLecture(Lecture lecture);
}
