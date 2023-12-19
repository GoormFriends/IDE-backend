package com.goorm.goormfriends.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WrapperResponse<T> {
    private T information;
}
