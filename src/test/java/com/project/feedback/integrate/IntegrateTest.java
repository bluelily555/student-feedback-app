package com.project.feedback.integrate;


import com.project.feedback.domain.dto.board.CommentWriteDto;
import com.project.feedback.domain.entity.CommentEntity;
import com.project.feedback.fixture.CommentDtoFixture;
import com.project.feedback.repository.CommentRepository;
import com.project.feedback.service.BoardService;
import com.project.feedback.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@ActiveProfiles("test") // local db에 row가 있는지 직접 확인 하고 싶을 때 씁니다.
public class IntegrateTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("comment write 성공")
    void commentWriteSuccessTest() {
        CommentWriteDto commentWriteDto = CommentDtoFixture.commentWriteDto(1l, "댓글입니다.");
        Long commentId = commentService.saveComment(commentWriteDto, 1l);
        assertEquals(1l, commentId);

        Optional<CommentEntity> commentEntity = commentRepository.findById(1l);

        assertEquals("댓글입니다.", commentEntity.get().getContent());
    }
}
