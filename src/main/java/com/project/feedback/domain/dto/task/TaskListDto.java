package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.dto.board.BoardWriteDto;
import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TaskListDto {

    private Long id;
    private String title;
    private String userName;
    private Long week;
    private Long day;
    private String status;
    private String courseName;
    private List<BoardWriteDto> boardLists;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static TaskListDto of(TaskEntity task) {
        List<BoardWriteDto> list = new ArrayList<>();
            for(BoardEntity boardEntity : task.getBoardEntities()){
                list.add(BoardWriteDto.of(boardEntity));
            }

        return TaskListDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .courseName(task.getCourseEntity().getName())
                .userName(task.getUser().getUserName())
                .week(task.getWeek())
                .status(task.getTaskStatus().toString())
                .day(task.getDayOfWeek())
                .boardLists(list)
                .createdAt(task.getCreatedAt())
                .lastModifiedAt(task.getLastModifiedAt())
                .build();
    }
}
