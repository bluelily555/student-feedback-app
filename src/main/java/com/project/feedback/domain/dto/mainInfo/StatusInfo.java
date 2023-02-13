package com.project.feedback.domain.dto.mainInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;




@Getter
@Builder
@Setter
public class StatusInfo {
    private Long taskId;
    private String taskName;
    private String taskStatus;

    public static StatusInfo of(Long taskId, String taskName, String status) {
        return StatusInfo.builder()
            .taskId(taskId)
            .taskName(taskName)
            .taskStatus(status)
            .build();
    }

}
