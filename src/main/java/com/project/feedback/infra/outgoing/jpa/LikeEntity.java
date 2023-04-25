package com.project.feedback.infra.outgoing.jpa;

import com.project.feedback.domain.enums.LikeContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@SQLDelete(sql = "UPDATE likes SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "likes")
public class LikeEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeContentType contentType;

    private boolean status;

    @Column(nullable = false)
    private Long contentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_user_id")
    private UserEntity fromUser;

    private LocalDateTime deletedAt;

    public static LikeEntity of(LikeContentType type, Long contentId, UserEntity fromUser) {
        return LikeEntity.builder()
                .contentType(type)
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
