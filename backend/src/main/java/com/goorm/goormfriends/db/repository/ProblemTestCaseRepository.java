package com.goorm.goormfriends.db.repository;


import com.goorm.goormfriends.db.entity.ProblemTestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemTestCaseRepository extends JpaRepository<ProblemTestCase, Long> {
    List<ProblemTestCase> findByProblemId(Long problemId);
}