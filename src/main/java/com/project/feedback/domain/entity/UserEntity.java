package com.project.feedback.domain.entity;

import com.project.feedback.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userName;


    private String realName;
    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "user")
    private List<TaskEntity> tasks;

    @OneToMany(mappedBy = "user")
    private List<BoardEntity> boardEntities;

    @OneToMany(mappedBy = "user")
    private List<UserTaskEntity> userTaskEntities;

    @OneToMany(mappedBy = "user")
    private List<CourseUserEntity> courseUserEntities;

    public void setRole(Role role) {
        this.role = role;
    }
    public void setPassword(String password){ this.password = password;}
}
