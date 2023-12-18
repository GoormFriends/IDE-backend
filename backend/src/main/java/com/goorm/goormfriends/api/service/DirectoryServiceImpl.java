package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.request.CreateDirectoryProblemRequest;
import com.goorm.goormfriends.api.dto.request.DeleteDirectoryProblemRequest;
import com.goorm.goormfriends.api.dto.request.DeleteDirectoryRequest;
import com.goorm.goormfriends.api.dto.request.UpdateDirectoryRequest;
import com.goorm.goormfriends.api.dto.response.DirectoryListResponse;
import com.goorm.goormfriends.api.dto.response.DirectoryProblemResponse;
import com.goorm.goormfriends.db.entity.CustomDirectory;
import com.goorm.goormfriends.db.entity.CustomDirectoryProblem;
import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.User;
import com.goorm.goormfriends.db.repository.CustomDirectoryProblemRepository;
import com.goorm.goormfriends.db.repository.CustomDirectoryRepository;
import com.goorm.goormfriends.db.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService{

    private final UserRepository userRepository;
    private final CustomDirectoryRepository customDirectoryRepository;
    private final CustomDirectoryProblemRepository customDirectoryProblemRepository;
    @Override
    public List<DirectoryListResponse> getCustomDirectory(int userId) throws Exception{
        List<DirectoryListResponse> result = new ArrayList<>();
        List<CustomDirectory> customDirectoryList = customDirectoryRepository.findByUserId(userId);
        for (CustomDirectory customDirectory : customDirectoryList) {
            List<CustomDirectoryProblem> directoryProblemList = customDirectoryProblemRepository
                    .findByCustomDirectoryId(customDirectory.getId());
            List<DirectoryProblemResponse> directoryProblemResponses = new ArrayList<>();
            for (CustomDirectoryProblem customDirectoryProblem : directoryProblemList) {
                DirectoryProblemResponse directoryProblemResponse = new DirectoryProblemResponse(customDirectoryProblem);
                directoryProblemResponses.add(directoryProblemResponse);
            }
            DirectoryListResponse directoryListResponse = new DirectoryListResponse(customDirectory,directoryProblemResponses);
            result.add(directoryListResponse);
        }
        return result;
    }

    @Override
    public void addDirectory(String email, String title) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Can't find user"));

        CustomDirectory customDirectory = new CustomDirectory(title, user.getId());
        customDirectoryRepository.save(customDirectory);
    }

    @Override
    public void updateDirectory(UpdateDirectoryRequest updateDirectoryRequest) throws Exception {
        Optional<CustomDirectory> optionalCustomDirectory = customDirectoryRepository.findById(updateDirectoryRequest.getDirectoryId());
        if (optionalCustomDirectory.isPresent()) {
            CustomDirectory customDirectory = optionalCustomDirectory.get();

            if (customDirectory.getUser().getId() != updateDirectoryRequest.getUserId()) {
                throw new IllegalArgumentException("User IDs do not match!");
            } else {
                customDirectory.setDirectory_name(updateDirectoryRequest.getNewDirectoryTitle());
                customDirectoryRepository.save(customDirectory);
            }
        } else {
            throw new IllegalArgumentException("CustomDirectory not found with ID: " + updateDirectoryRequest.getDirectoryId());
        }
    }

    @Override
    public void deleteDirectory(DeleteDirectoryRequest deleteDirectoryRequest) throws Exception {
        if (customDirectoryRepository.existsById(deleteDirectoryRequest.getDirectoryId())) {
            customDirectoryRepository.deleteById(deleteDirectoryRequest.getDirectoryId());
        }else {
            throw new IllegalArgumentException("CustomDirectory not found with ID: " + deleteDirectoryRequest.getDirectoryId());
        }
    }

    @Override
    public DirectoryProblemResponse addDirectoryProblem(CreateDirectoryProblemRequest createDirectoryProblemRequest)
            throws Exception {
        // 문제 있는지 확인
//        if (!problemRepository.existsById(createDirectoryProblemRequest.getProblemId())) {
//            throw new IllegalArgumentException("Problem not found with ID: " + createDirectoryProblemRequest.getProblemId());
//        }
        // directoryId 가 있는지 확인
        if (!customDirectoryRepository.existsById(createDirectoryProblemRequest.getDirectoryId())) {
            throw new IllegalArgumentException("CustomDirectory not found with ID: " + createDirectoryProblemRequest.getDirectoryId());
        }
        // 그렇다면 넣기
        Optional<CustomDirectory> customDirectory = customDirectoryRepository.findById(createDirectoryProblemRequest.getDirectoryId());
        //Problem problem = problemRepository.findById(createDirectoryProblemRequest.getDirectoryId());
        CustomDirectoryProblem customDirectoryProblem = new CustomDirectoryProblem(createDirectoryProblemRequest);
        customDirectoryProblemRepository.save(customDirectoryProblem);
        return new DirectoryProblemResponse(customDirectoryProblem);
    }

    @Override
    public void deleteDirectoryProblem(DeleteDirectoryProblemRequest deleteDirectoryProblemRequest) throws Exception {
        if(!customDirectoryProblemRepository.existsById(deleteDirectoryProblemRequest.getDirectoryProblemId())) {
            throw new IllegalArgumentException("CustomDirectoryProblem not found with ID: " + deleteDirectoryProblemRequest.getDirectoryProblemId());
        } else {
            customDirectoryProblemRepository.deleteById(deleteDirectoryProblemRequest.getDirectoryProblemId());
        }
    }
}
