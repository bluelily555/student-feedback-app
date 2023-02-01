package com.project.feedback.domain.dto.user;

import com.project.feedback.domain.entity.UserEntity;
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
public class UserListResponse {
    private List<UserListDto> content;
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

    public UserListResponse(List<UserListDto> content, Pageable pageable, Page<UserEntity> users) {
        this.content = content;
        this.pageable = pageable;
        this.last = users.isLast();
        this.totalPages = users.getTotalPages();
        this.totalElements = users.getTotalElements();
        this.size = users.getSize();
        this.number = users.getNumber();
        this.sort = users.getSort();
        this.first = users.isFirst();
        this.numberOfElements = users.getNumberOfElements();
        this.empty = users.isEmpty();
    }
}
