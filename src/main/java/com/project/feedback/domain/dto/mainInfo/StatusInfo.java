package com.project.feedback.domain.dto.mainInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;




@Getter
@Builder
@Setter
public class StatusInfo {
    private Long taskId;
    private String taskStatus;

    public static StatusInfo of(Long taskId, String status) {
        return StatusInfo.builder()
            .taskId(taskId)
            .taskStatus(status)
            .build();
    }

}
