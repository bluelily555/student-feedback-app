package com.project.feedback.domain.dto.mainInfo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseTaskListResponse {
    // Student
    private List<StudentInfo> studentInfoList;
    // Task
    private List<TaskInfo> taskInfoList;

}
