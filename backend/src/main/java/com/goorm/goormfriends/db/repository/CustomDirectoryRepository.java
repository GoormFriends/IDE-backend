package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.CustomDirectory;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomDirectoryRepository extends JpaRepository<CustomDirectory, Integer> {
    List<CustomDirectory> findByUserId(Integer userId);

    Optional<CustomDirectory> findById(Long id);

    boolean existsById(Long directoryId);

    void deleteById(Long directoryId);

    @Query("SELECT cdp.customDirectory FROM CustomDirectoryProblem cdp WHERE cdp.problem.id = :problemId")
    List<CustomDirectory> findAllByProblemId(@Param("problemId") String problemId);
}
