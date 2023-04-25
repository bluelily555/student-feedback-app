package com.project.feedback.domain.dto.task;

import com.project.feedback.infra.outgoing.jpa.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskListResponse {

    private List<TaskListDto> content;
    private Pageable pageable;
    private boolean last;
    private int totalPages;
    private Long totalElements;
    private int size;
    private int number;
    private Sort sort;
    private boolean first;
    private int numberOfElements;
    private boolean empty;

    public TaskListResponse(List<TaskListDto> content, Pageable pageable, Page<TaskEntity> tasks) {
        this.content = content;
        this.pageable = pageable;
        this.last = tasks.isLast();
        this.totalPages = tasks.getTotalPages();
        this.totalElements = tasks.getTotalElements();
        this.size = tasks.getSize();
        this.number = tasks.getNumber();
        this.sort = tasks.getSort();
        this.first = tasks.isFirst();
        this.numberOfElements = tasks.getNumberOfElements();
        this.empty = tasks.isEmpty();
    }
}
