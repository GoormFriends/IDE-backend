package com.goorm.goormfriends.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomDirectoryInfo {
    private Long customDirectoryId;
    private String customDirectoryName;
}
