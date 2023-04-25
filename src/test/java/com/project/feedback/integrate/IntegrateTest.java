package com.project.feedback.integrate;


import com.project.feedback.repository.CommentRepository;
import com.project.feedback.application.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@ActiveProfiles("test") // local db에 row가 있는지 직접 확인 하고 싶을 때 씁니다.
public class IntegrateTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

//    @Test
//    @DisplayName("comment write 성공")
//    void commentWriteSuccessTest() {
//        CommentCreateRequest commentCreateRequest = CommentDtoFixture.commentCreateRequest("댓글입니다.");
//        CommentCreateResponse res = commentService.saveComment(commentCreateRequest, "test",1l);
//        assertEquals(1l, res.getCodeId());
//
//        Optional<CommentEntity> commentEntity = commentRepository.findById(1l);
//
//        assertEquals("댓글입니다.", commentEntity.get().getComment());
//    }
}
