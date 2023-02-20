package com.project.feedback.domain.dto.board;


import com.project.feedback.domain.entity.CodeEntity;
import com.project.feedback.domain.entity.TaskEntity;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeWriteDto {

    private Long id;
    private TaskEntity taskId;
    private String writer;
    private String content;
    private String codeContent;
    private String title;
    private String userName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CodeEntity toEntity(){
        CodeEntity codeEntity = CodeEntity.builder()
                .writer(writer)
                .taskId(taskId)
                .content(content)
                .codeContent(codeContent)
                .title(title)
                .userName(userName)
                .build();
        return codeEntity;
    }

//    @Builder
//    public CodeWriteDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate){
//        this.id = id;
//        this.writer = writer;
//        this.content = content;
//        this.title = title;
//        this.createdDate = createdDate;
//        this.modifiedDate = modifiedDate;
//    }
}
