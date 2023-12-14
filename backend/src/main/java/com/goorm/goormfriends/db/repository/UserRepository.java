package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<User> findByEmail(String email);
    List<User> findByEmailContainingOrNicknameContaining(String email, String nickname);
    void deleteByEmail(String email);
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
