package com.project.feedback.domain.dto.board;

import com.project.feedback.infra.outgoing.entity.BoardEntity;
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
public class BoardListResponse {
    private List<BoardListDto> content;
    private Pageable pageable;
    private boolean last;
    private int totalPages;
    private Long totalElements;
    private Sort sort;
    private boolean first;
    private boolean empty;
    public BoardListResponse(List<BoardListDto> content, Pageable pageable, Page<BoardEntity> boards){
        this.content = content;
        this.pageable = pageable;
        this.last = boards.isLast();
        this.totalPages = boards.getTotalPages();
        this.totalElements = boards.getTotalElements();
        this.first = boards.isFirst();
        this.empty = boards.isEmpty();
        this.sort = boards.getSort().ascending();
    }

}
