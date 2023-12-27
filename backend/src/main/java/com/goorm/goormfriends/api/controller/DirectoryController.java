package com.goorm.goormfriends.api.controller;

import com.goorm.goormfriends.api.dto.request.*;
import com.goorm.goormfriends.api.dto.response.DirectoryListResponse;
import com.goorm.goormfriends.api.dto.response.DirectoryProblemResponse;
import com.goorm.goormfriends.api.service.DirectoryService;
import com.goorm.goormfriends.api.service.UserService;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("directory")
@RequiredArgsConstructor
public class DirectoryController {

    private final DirectoryService directoryService;
    private final UserService userService;

    @GetMapping
    public List<DirectoryListResponse> getDirectoryList(@AuthenticationPrincipal User user,
                                                                  @RequestParam(name="userId") Long userId) throws Exception {

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

    @DeleteMapping
    public ResponseEntity<Void> deleteDirectory(@AuthenticationPrincipal User user,
                                                @RequestBody DeleteDirectoryRequest deleteDirectoryRequest) throws Exception {

        String userEmail = userService.findUserEmailByUserId(deleteDirectoryRequest.getUserId());
        if (!user.getUsername().equals(userEmail)) {
            throw new IllegalArgumentException("Usernames do not match!");
        } else {
            directoryService.deleteDirectory(deleteDirectoryRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("problem")
    public ResponseEntity<Map<String, Object>> addDirectoryProblem(@AuthenticationPrincipal User user,
                                                                   @RequestBody DirectoryProblemRequest directoryProblemRequest) throws Exception {

        String userEmail = userService.findUserEmailByUserId(directoryProblemRequest.getUserId());
        if (!user.getUsername().equals(userEmail)) {
            throw new IllegalArgumentException("Usernames do not match!");
        } else {
            Map<String, Object> resultMap = new HashMap<>();
            DirectoryProblemResponse directoryProblemResponse = directoryService.addDirectoryProblem(directoryProblemRequest);
            resultMap.put("directoryProblemResponse", directoryProblemResponse);
            return new ResponseEntity<>(resultMap,HttpStatus.OK);
        }
    }

    @DeleteMapping("problem")
    public ResponseEntity<Void> deleteDirectoryProblem(@AuthenticationPrincipal User user,
                                                       @RequestBody DirectoryProblemRequest directoryProblemRequest) throws Exception {
        String userEmail = userService.findUserEmailByUserId(directoryProblemRequest.getUserId());
        if (!user.getUsername().equals(userEmail)) {
            throw new IllegalArgumentException("Usernames do not match!");
        } else {
            directoryService.deleteDirectoryProblem(directoryProblemRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
