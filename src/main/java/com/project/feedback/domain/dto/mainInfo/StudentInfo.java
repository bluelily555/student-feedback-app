package com.project.feedback.domain.dto.mainInfo;

import com.project.feedback.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class StudentInfo {
    private Long id;
    private String realName;
    private List<StatusInfo> status;

    public static StudentInfo of(User user, List<StatusInfo> status) {
        return StudentInfo.builder()
            .id(user.getId())
            .realName(user.getRealName())
            .status(status)
            .build();
    }

    public static StudentInfo of(User user) {
        return StudentInfo.builder()
            .id(user.getId())
            .realName(user.getRealName())
            .build();
    }
}
