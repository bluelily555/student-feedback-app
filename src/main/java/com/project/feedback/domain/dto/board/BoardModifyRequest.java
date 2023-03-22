package com.project.feedback.domain.dto.board;

import com.project.feedback.domain.entity.BoardEntity;
import com.project.feedback.domain.entity.TaskEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardModifyRequest {
    private String title;
    private Long taskId;
    private String content;
    private String codeContent;
    private String language;
    private List<String> deleteImageNames;

    @Override
    public String toString() {
        return "BoardModifyRequest{" +
                "title='" + title + '\'' +
                ", taskId=" + taskId +
                ", content='" + content + '\'' +
                ", codeContent='" + codeContent + '\'' +
                ", language='" + language + '\'' +
                ", deleteImageNames=" + deleteImageNames +
                '}';
    }

    public BoardEntity toEntity(TaskEntity task) {
        return BoardEntity.builder()
                .title(title)
                .taskEntity(task)
                .content(content)
                .codeContent(codeContent)
                .language(language)
                .build();
    }
}
