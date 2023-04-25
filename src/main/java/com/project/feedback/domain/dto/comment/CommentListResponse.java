package com.project.feedback.domain.dto.comment;

import com.project.feedback.infra.outgoing.entity.CommentEntity;
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
public class CommentListResponse {

    private List<CommentListDto> content;
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

    public CommentListResponse(List<CommentListDto> content, Pageable pageable, Page<CommentEntity> comments) {
        this.content = content;
        this.pageable = pageable;
        this.last = comments.isLast();
        this.totalPages = comments.getTotalPages();
        this.totalElements = comments.getTotalElements();
        this.size = comments.getSize();
        this.number = comments.getNumber();
        this.sort = comments.getSort();
        this.first = comments.isFirst();
        this.numberOfElements = comments.getNumberOfElements();
        this.empty = comments.isEmpty();
    }
}
