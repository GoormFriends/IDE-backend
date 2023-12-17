package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.CustomDirectory;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomDirectoryRepository extends JpaRepository<CustomDirectory, Integer> {
    List<CustomDirectory> findByUserId(Integer userId);

    Optional<CustomDirectory> findById(Integer id);
}
