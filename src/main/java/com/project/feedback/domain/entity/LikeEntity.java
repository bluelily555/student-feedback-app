package com.project.feedback.domain.entity;

import com.project.feedback.domain.enums.LikeContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name ="likes")
public class LikeEntity extends TimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeContentType type;

    private boolean status;

    @Column(nullable = false)
    private Long contentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_user_id")
    private UserEntity fromUser;

    public static LikeEntity of(LikeContentType type, Long contentId, UserEntity fromUser) {
        return LikeEntity.builder()
                .type(type)
                .fromUser(fromUser)
                .contentId(contentId)
                .build();
    }

    public void like() {
        this.status = true;
    }

    public void unlike() {
        this.status = false;
    }
}
