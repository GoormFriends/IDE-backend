package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.Ide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdeRepository extends JpaRepository<Ide, Long> {
    // 특정 Problem ID에 연결된 모든 Ide를 조회하는 메소드
    List<Ide> findAllByProblemId(Long problemId);

    // 사용자 ID와 문제 ID를 기반으로 Ide를 찾는 메소드
    Optional<Ide> findByUserIdAndProblemId(Long userId, Long problemId);
}

