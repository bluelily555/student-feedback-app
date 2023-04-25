package com.project.feedback.infra.outgoing.entity;

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
public class UserEntity extends BaseEntity {
    private static final String DEFAULT_PROFILE_IMG = "https://bootdey.com/img/Content/avatar/avatar7.png";
    private static final String PROFILE_PATH = "/images/profile/";

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

    private String profile;

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

    public String getProfilePath() {
        return profile == null ? DEFAULT_PROFILE_IMG : PROFILE_PATH + profile;
    }

    public void updateProfile(String profileImgName) {
        this.profile = profileImgName;
    }

    public void deleteProfile() {
        this.profile = null;
    }

    public boolean isManager() {
        return this.role == Role.ROLE_ADMIN || this.role == Role.ROLE_MENTOR;
    }
}
