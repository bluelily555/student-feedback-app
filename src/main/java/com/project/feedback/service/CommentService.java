package com.project.feedback.service;

import com.project.feedback.domain.dto.board.CommentWriteDto;
import com.project.feedback.domain.entity.CommentEntity;
import com.project.feedback.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentService {
    private CommentRepository commentRepository;

    private CommentWriteDto convertEntityToDto(CommentEntity commentEntity){
        return CommentWriteDto.builder()
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .writer(commentEntity.getWriter())
                .boardId(commentEntity.getBoardId())
                .createdDate(commentEntity.getCreatedDate())
                .build();
    }
    @Transactional
    public List<CommentWriteDto> searchPosts(Long post_id){
        List<CommentEntity> commentEntities = commentRepository.findCommentEntityByBoardId(post_id);
        List<CommentWriteDto> commentWriteDtoList = new ArrayList<>();

        if(commentEntities.isEmpty()) return commentWriteDtoList;
        for(CommentEntity commentEntity : commentEntities){
            commentWriteDtoList.add(this.convertEntityToDto(commentEntity));
        }
        return commentWriteDtoList;
    }

    @Transactional
    public List<CommentWriteDto> getCommentList(){
        List<CommentEntity> commentEntities = commentRepository.findAll();
        List<CommentWriteDto> commentWriteDtoList = new ArrayList<>();

        for(CommentEntity commentEntity : commentEntities){
            CommentWriteDto commentWriteDto = CommentWriteDto.builder()
                    .id(commentEntity.getId())
                    .content(commentEntity.getContent())
                    .writer(commentEntity.getWriter())
                    .boardId(commentEntity.getBoardId())
                    .createdDate(commentEntity.getCreatedDate())
                    .build();
            commentWriteDtoList.add(commentWriteDto);
        }
        return commentWriteDtoList;
    }
    public Long saveComment(CommentWriteDto commentWriteDto, Long boardId){
        CommentEntity commentEntity = commentWriteDto.toEntity();
        commentEntity.setBoardId(boardId);
        CommentEntity savedCommentEntity = commentRepository.save(commentEntity);
        return savedCommentEntity.getId();
    }
    @Transactional
    public CommentWriteDto getPost(Long id){
        Optional<CommentEntity> commentEntityWrapper = commentRepository.findById(id);
        CommentEntity commentEntity = commentEntityWrapper.get();
        CommentWriteDto commentWriteDto = CommentWriteDto.builder()
                .id(commentEntity.getId())
                .boardId(commentEntity.getBoardId())
                .content(commentEntity.getContent())
                .writer(commentEntity.getWriter())
                .createdDate(commentEntity.getCreatedDate())
                .build();
        return  commentWriteDto;
    }
    @Transactional
    public void deletePost(Long id){
        commentRepository.deleteById(id);
    }
}
