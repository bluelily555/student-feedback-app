package com.project.feedback.domain.dto.course;

import com.project.feedback.domain.entity.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateRequest {
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public CourseEntity toEntity() {
        return CourseEntity.builder()
                .name(this.name)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
