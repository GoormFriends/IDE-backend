package com.goorm.goormfriends.api.compiler.core;

import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor

public class IdeResponse {

    private State state;

    public static IdeResponse from(Ide ide) {

        return new IdeResponse(ide.getState());
    }

}
