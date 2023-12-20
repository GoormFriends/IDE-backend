package com.goorm.goormfriends.api.compiler.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WrapperResponse<T> {
    private T information;
}
