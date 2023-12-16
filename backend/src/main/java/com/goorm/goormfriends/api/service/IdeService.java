package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.request.IdeRequest;
import com.goorm.goormfriends.api.dto.response.IdeResponse;
import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.User;
import com.goorm.goormfriends.db.repository.IdeRepository;
import com.goorm.goormfriends.db.repository.ProblemRepository;
import com.goorm.goormfriends.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdeService {
    private final IdeRepository ideRepository;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;

    public IdeResponse addInput(IdeRequest request) {
        Optional<Problem> problem = problemRepository.findById(request.getProblem().getId());
        if (!problem.isPresent()) {
            throw new RuntimeException("Problem not found with id: " + request.getProblem().getId());
        }

        Optional<User> user = userRepository.findById(request.getUser().getId());
        if (!user.isPresent()) {
            throw new RuntimeException("User not found with id: " + request.getUser().getId());
        }

        Ide ide = new Ide();
        ide.setProblem(problem.get());
        ide.setUsercode(request.getUsercode());
        ide.setUser(user.get());

        return IdeResponse.from(ideRepository.save(ide));
    }
/*
    // 코드 출력
    public IdeResponse getOutput(IdeRequest request) {
        return IdeRepository.findById(id).orElse(null);
    }

 */
}
