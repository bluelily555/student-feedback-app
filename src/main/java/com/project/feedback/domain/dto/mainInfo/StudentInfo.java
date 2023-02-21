package com.project.feedback.domain.dto.mainInfo;

import com.project.feedback.domain.entity.UserEntity;
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
    private String userName;
    private List<StatusInfo> status;

    public static StudentInfo of(UserEntity user, List<StatusInfo> status) {
        return StudentInfo.builder()
            .id(user.getId())
            .realName(user.getRealName())
            .userName(user.getUserName())
            .status(status)
            .build();
    }

    public static StudentInfo of(UserEntity user) {
        return StudentInfo.builder()
            .id(user.getId())
            .realName(user.getRealName())
            .userName(user.getUserName())
            .build();
    }
}
