package com.project.nexedu.domain.comment;

import com.project.nexedu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByWriterOrderByIdDesc(User writer);

    void deleteByWriter(User writer);
}
