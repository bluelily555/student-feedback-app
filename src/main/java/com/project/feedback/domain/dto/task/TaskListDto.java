package com.project.feedback.domain.dto.task;

import com.project.feedback.domain.dto.board.BoardListDto;
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
public class TaskListDto {

    private Long id;
    private String title;
    private String userName;
    private Long week;
    private Long day;
    private String status;
    private String courseName;
    private List<BoardListDto> boardLists;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static TaskListDto of(TaskEntity task) {
        List<BoardListDto> list = new ArrayList<>();
            for(BoardEntity boardEntity : task.getBoardEntities()){
                list.add(BoardListDto.of(boardEntity));
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
