package com.project.feedback.service;

import com.project.feedback.domain.dto.board.CodeWriteDto;
import com.project.feedback.domain.entity.CodeEntity;
import com.project.feedback.repository.CodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CodeService {
    private CodeRepository codeRepository;

    @Transactional
    public Long saveCode(CodeWriteDto codeWriteDto){
        CodeEntity codeEntity = codeWriteDto.toEntity();
        CodeEntity savedCodeEntity = codeRepository.save(codeEntity);
        return savedCodeEntity.getId();
    }
    @Transactional
    public List<CodeWriteDto> searchAllCode(){
        List<CodeEntity> codeEntities = codeRepository.findAll();
        List<CodeWriteDto> codeWriteDtoList = new ArrayList<>();

        for(CodeEntity codeEntity : codeEntities){
            CodeWriteDto codeWriteDto = CodeWriteDto.builder()
                    .id(codeEntity.getId())
                    .content(codeEntity.getContent())
                    .writer(codeEntity.getWriter())
                    .title(codeEntity.getTitle())
                    .createdDate(codeEntity.getCreatedDate())
                    .build();
            codeWriteDtoList.add(codeWriteDto);
        }
        return codeWriteDtoList;
    }
    @Transactional
    public void deleteCode(Long boardId){
        codeRepository.deleteById(boardId);
    }
    @Transactional
    public CodeWriteDto getCodeDetail(Long boardId){
        Optional<CodeEntity> codeEntityWrapper = codeRepository.findById(boardId);
        CodeEntity codeEntity = codeEntityWrapper.get();
        CodeWriteDto codeWriteDto = CodeWriteDto.builder()
                .id(codeEntity.getId())
                .title(codeEntity.getTitle())
                .content(codeEntity.getContent())
                .writer(codeEntity.getWriter())
                .createdDate(codeEntity.getCreatedDate())
                .build();
        return codeWriteDto;
    }
}
