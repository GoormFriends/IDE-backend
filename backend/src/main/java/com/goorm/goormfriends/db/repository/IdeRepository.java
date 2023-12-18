package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.Ide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeRepository extends JpaRepository<Ide, Long> {
    // 특정 Problem ID에 연결된 모든 Ide를 조회하는 메소드
    List<Ide> findAllByProblemId(String problemId);
}

