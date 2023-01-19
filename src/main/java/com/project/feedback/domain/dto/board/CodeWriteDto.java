package com.project.feedback.domain.dto.board;


import com.project.feedback.domain.entity.CodeEntity;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeWriteDto {

    private Long id;
    private String writer;
    private String content;
    private Long boardId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CodeEntity toEntity(){
        CodeEntity codeEntity = CodeEntity.builder()
                .writer(writer)
                .content(content)
                .boardId(boardId)
                .build();
        return codeEntity;
    }

    @Builder
    public CodeWriteDto(Long id, Long boardId, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate){
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.boardId = boardId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
