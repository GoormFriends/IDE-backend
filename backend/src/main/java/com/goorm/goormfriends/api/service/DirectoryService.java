package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.request.*;
import com.goorm.goormfriends.api.dto.response.DirectoryListResponse;
import com.goorm.goormfriends.api.dto.response.DirectoryProblemResponse;
import com.goorm.goormfriends.db.entity.CustomDirectory;

import java.util.List;

public interface DirectoryService {
    List<DirectoryListResponse> getCustomDirectory(int userId) throws Exception;
    void addDirectory(String email, String title) throws Exception;

    void updateDirectory(UpdateDirectoryRequest updateDirectoryRequest) throws Exception;

    void deleteDirectory(DeleteDirectoryRequest deleteDirectoryRequest) throws Exception;

    DirectoryProblemResponse addDirectoryProblem(CreateDirectoryProblemRequest createDirectoryProblemRequest)
            throws Exception;

    void deleteDirectoryProblem(DeleteDirectoryProblemRequest deleteDirectoryProblemRequest) throws Exception;
}
