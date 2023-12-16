package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.Ide;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor

public class IdeResponse {

    private Long id;
    private boolean solved;
    private String usercode;
    private Long userId;
    private Long problemId;

    public static IdeResponse from(Ide ide) {

        return new IdeResponse(
                ide.getId(),
                ide.isSolved(),
                ide.getUsercode(),
                ide.getUser() != null ? ide.getUser().getId() : null,
                ide.getProblem() != null ? ide.getProblem().getId() : null
        );
    }

}
