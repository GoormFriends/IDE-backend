package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.Ide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeRepository extends JpaRepository<Ide, Long> {

}
