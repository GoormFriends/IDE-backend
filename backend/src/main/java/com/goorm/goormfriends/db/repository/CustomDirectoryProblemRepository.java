package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.CustomDirectoryProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomDirectoryProblemRepository extends JpaRepository<CustomDirectoryProblem, Long> {
    List<CustomDirectoryProblem> findByCustomDirectoryId(Long customDirectoryId);
    boolean existsByCustomDirectoryIdAndProblemId(Long customDirectoryId, Long ProblemId);

    CustomDirectoryProblem findByCustomDirectoryIdAndProblemId(Long customDirectoryId, Long ProblemId);
    // Problem ID를 기준으로 CustomDirectoryProblem 객체들을 조회하는 메소드
    List<CustomDirectoryProblem> findByProblemId(Long problemId);
}
