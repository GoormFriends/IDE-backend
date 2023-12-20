package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.ProblemTestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemTestCaseRepository extends JpaRepository<ProblemTestCase, Long> {
    List<ProblemTestCase> findByProblemId(Long problemId);
}