package com.project.feedback.infra.outgoing.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@SQLDelete(sql = "UPDATE board SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@Table(name ="board")
public class BoardEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_entity_id")
    private TaskEntity taskEntity;

    @OneToMany(mappedBy = "boardEntity", orphanRemoval = true)
    private List<CommentEntity> comments;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ImageEntity> images = new ArrayList<>();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String codeContent;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String language;

    @Column(length = 128, nullable = false)
    private String title;

    //삭제 날짜
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void modify(BoardEntity modifyEntity) {
        this.title = modifyEntity.getTitle();
        this.taskEntity = modifyEntity.getTaskEntity();
        this.content = modifyEntity.getContent();
        this.codeContent = modifyEntity.getCodeContent();
        this.language = modifyEntity.getLanguage();
    }

    public void addImage(ImageEntity image) {
        image.setBoard(this);
        this.images.add(image);
    }

    public void deleteImageAll(Collection<ImageEntity> images) {
        this.images.removeAll(images);
    }

    public boolean equalsOwner(UserEntity user) {
        return this.user.equals(user);
    }
}
