package com.project.feedback.domain.entity;

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
@Table(name ="image")
public class ImageEntity extends TimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    public void setBoard(BoardEntity board) {
        this.board = board;
    }

    public static ImageEntity of(String fileName) {
        return ImageEntity.builder()
                .name(fileName)
                .build();
    }
}
