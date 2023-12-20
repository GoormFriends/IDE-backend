package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.CustomDirectoryProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomDirectoryProblemRepository extends JpaRepository<CustomDirectoryProblem, Long> {
    List<CustomDirectoryProblem> findByCustomDirectoryId(Long customDirectoryId);
    boolean existsByCustomDirectoryIdAndProblemId(Long customDirectoryId, Long ProblemId);
}
