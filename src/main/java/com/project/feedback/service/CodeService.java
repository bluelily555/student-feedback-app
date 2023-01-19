package com.project.feedback.service;

import com.project.feedback.domain.dto.board.CodeWriteDto;
import com.project.feedback.domain.entity.CodeEntity;
import com.project.feedback.repository.CodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
                    .boardId(codeEntity.getBoardId())
                    .createdDate(codeEntity.getCreatedDate())
                    .build();
            codeWriteDtoList.add(codeWriteDto);
        }
        return codeWriteDtoList;
    }
}
