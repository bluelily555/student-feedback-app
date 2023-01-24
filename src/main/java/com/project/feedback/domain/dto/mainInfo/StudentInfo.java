package com.project.feedback.domain.dto.mainInfo;

import com.project.feedback.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentInfo {
    private Long id;
    private String realName;

    // TO DO : task에 대한 진행상황

    public static StudentInfo of(User user) {
        return StudentInfo.builder()
            .id(user.getId())
            .realName(user.getRealName())
            .build();
    }
}
