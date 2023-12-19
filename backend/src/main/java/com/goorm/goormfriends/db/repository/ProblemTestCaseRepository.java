package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.ProblemTestCase;

import java.util.List;

public interface ProblemTestCaseRepository {
    List<ProblemTestCase> findByProblemId(Long problemId);
}