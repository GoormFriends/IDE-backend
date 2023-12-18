package com.goorm.goormfriends.api.controller;

import com.goorm.goormfriends.api.dto.request.CreateDirectoryRequest;
import com.goorm.goormfriends.api.dto.request.UpdateDirectoryRequest;
import com.goorm.goormfriends.api.dto.response.DirectoryListResponse;
import com.goorm.goormfriends.api.service.DirectoryService;
import com.goorm.goormfriends.api.service.UserService;
import com.goorm.goormfriends.db.entity.CustomDirectory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("directory")
@RequiredArgsConstructor
public class DirectoryController {

    private final DirectoryService directoryService;
    private final UserService userService;

    @GetMapping
    public List<DirectoryListResponse> getDirectoryList(@AuthenticationPrincipal User user,
                                                                  @RequestBody Integer userId) throws Exception {

        String userEmail = userService.findUserEmailByUserId(userId);

        if (!user.getUsername().equals(userEmail)) {
            throw new IllegalArgumentException("Usernames do not match!");
        } else {
            return directoryService.getCustomDirectory(userId);
        }
    }

    @PostMapping
    public ResponseEntity<Void> addDirectory (@AuthenticationPrincipal User user, @RequestBody CreateDirectoryRequest createDirectoryRequest) throws Exception {

        String userEmail = userService.findUserEmailByUserId(createDirectoryRequest.getUserId());
        if (!user.getUsername().equals(userEmail)) {
            throw new IllegalArgumentException("Usernames do not match!");
        } else {
            directoryService.addDirectory(userEmail, createDirectoryRequest.getDirectoryTitle());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateDirectory(@AuthenticationPrincipal User user,
                                                @RequestBody UpdateDirectoryRequest updateDirectoryRequest) throws Exception {
        String userEmail = userService.findUserEmailByUserId(updateDirectoryRequest.getUserId());
        if (!user.getUsername().equals(userEmail)) {
            throw new IllegalArgumentException("Usernames do not match!");
        } else {
            directoryService.updateDirectory(updateDirectoryRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
