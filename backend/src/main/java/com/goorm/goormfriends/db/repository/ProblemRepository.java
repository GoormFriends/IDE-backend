package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    // 사용자 ID를 사용하여 Problem을 조회하는 사용자 정의 쿼리
    @Query("SELECT p FROM Problem p JOIN p.ides i WHERE i.user.id = :userId")
    List<Problem> findAllByUserId(@Param("userId") Long userId);

    // 사용자 아이디만을 통해 작성코드 관리하는 problem 환경 조회
    Optional<Problem> findUserCodeByUserId(Long userId);


}