package com.project.nexedu.domain.lecture;

import com.project.nexedu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    List<Lecture> findByInstructor(User instructor);

    void deleteByInstructor(User instructor);
}
