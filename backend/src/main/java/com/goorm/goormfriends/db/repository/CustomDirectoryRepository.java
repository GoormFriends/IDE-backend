package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.CustomDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomDirectoryRepository extends JpaRepository<CustomDirectory, Long> {

    @Query("SELECT cdp.customDirectory FROM CustomDirectoryProblem cdp WHERE cdp.problem.id = :problemId")
    List<CustomDirectory> findAllByProblemId(@Param("problemId") String problemId);
}
