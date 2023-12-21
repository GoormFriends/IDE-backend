package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.request.*;
import com.goorm.goormfriends.api.dto.response.DirectoryListResponse;
import com.goorm.goormfriends.api.dto.response.DirectoryProblemResponse;

import java.util.List;

public interface DirectoryService {
    List<DirectoryListResponse> getCustomDirectory(Long userId) throws Exception;
    void addDirectory(String email, String title) throws Exception;

    void updateDirectory(UpdateDirectoryRequest updateDirectoryRequest) throws Exception;

    void deleteDirectory(DeleteDirectoryRequest deleteDirectoryRequest) throws Exception;

    DirectoryProblemResponse addDirectoryProblem(DirectoryProblemRequest directoryProblemRequest)
            throws Exception;

    void deleteDirectoryProblem(DirectoryProblemRequest directoryProblemRequest) throws Exception;
}
