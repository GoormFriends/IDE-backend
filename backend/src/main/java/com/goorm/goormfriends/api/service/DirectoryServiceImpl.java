package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.request.UpdateDirectoryRequest;
import com.goorm.goormfriends.api.dto.response.DirectoryListResponse;
import com.goorm.goormfriends.api.dto.response.DirectoryProblemResponse;
import com.goorm.goormfriends.db.entity.CustomDirectory;
import com.goorm.goormfriends.db.entity.CustomDirectoryProblem;
import com.goorm.goormfriends.db.entity.User;
import com.goorm.goormfriends.db.repository.CustomDirectoryProblemRepository;
import com.goorm.goormfriends.db.repository.CustomDirectoryRepository;
import com.goorm.goormfriends.db.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
}
