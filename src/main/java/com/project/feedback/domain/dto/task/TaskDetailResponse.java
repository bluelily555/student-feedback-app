package com.project.feedback.domain.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.feedback.domain.TaskStatus;
import com.project.feedback.domain.dto.board.BoardListDto;
import com.project.feedback.domain.dto.course.CourseInfo;
import com.project.feedback.infra.outgoing.jpa.BoardEntity;
import com.project.feedback.infra.outgoing.jpa.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TaskDetailResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private CourseInfo courseInfo;
    private List<BoardListDto> boards;
    private Long week;
    private Long day;
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;

    public static TaskDetailResponse of(TaskEntity task) {
        List<BoardListDto> list = new ArrayList<>();
        for(BoardEntity boardEntity : task.getBoardEntities()){
            list.add(BoardListDto.of(boardEntity));
        }
        return TaskDetailResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .week(task.getWeek())
                .day(task.getDayOfWeek())
                .taskStatus(task.getTaskStatus())
                .courseInfo(CourseInfo.fromEntity(task.getCourseEntity()))
                .boards(list)
                .userName(task.getUser().getUserName())
                .createdAt(task.getCreatedAt())
                .lastModifiedAt(task.getLastModifiedAt())
                .build();
    }
}
