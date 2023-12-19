package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    // 사용자 ID를 사용하여 Problem을 조회하는 사용자 정의 쿼리
    @Query("SELECT p FROM Problem p JOIN p.ides i WHERE i.user.id = :userId")
    List<Problem> findAllByUserId(@Param("userId") Long userId);
}