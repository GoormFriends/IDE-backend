package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.request.CreateDirectoryRequest;
import com.goorm.goormfriends.api.dto.request.UpdateDirectoryRequest;
import com.goorm.goormfriends.api.dto.response.DirectoryListResponse;
import com.goorm.goormfriends.db.entity.CustomDirectory;

import java.util.List;

public interface DirectoryService {
    List<DirectoryListResponse> getCustomDirectory(int userId) throws Exception;
    void addDirectory(String email, String title) throws Exception;

    void updateDirectory(UpdateDirectoryRequest updateDirectoryRequest) throws Exception;
}
