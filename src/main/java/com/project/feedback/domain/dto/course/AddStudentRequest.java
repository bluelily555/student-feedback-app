package com.project.feedback.domain.dto.course;

import com.project.feedback.infra.outgoing.jpa.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentRequest {
  //  private Long courseId;
    private String courseName;
    private List<UserEntity> userList; // multi-checkbox
}
