package com.project.feedback.domain.dto.mainInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;




@Getter
@Builder
@Setter
public class StatusInfo {
    private String taskStatus;

    public static StatusInfo of(String status) {
        return StatusInfo.builder()
            .taskStatus(status)
            .build();
    }

}
