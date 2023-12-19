package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.ProblemTestCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor

public class IdeResponse {

    private Long userId;
    private Long problemId;
    private String usercode;

    public static IdeResponse from(Ide ide) {

        return new IdeResponse(

                ide.getUser() != null ? ide.getUser().getId() : null,
                ide.getProblem() != null ? ide.getProblem().getId() : null,
                ide.getUsercode()
        );
    }

}
