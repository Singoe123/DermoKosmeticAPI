package com.dk.dermokometicapi.mappers;

import com.dk.dermokometicapi.models.dto.QuestionRequestDTO;
import com.dk.dermokometicapi.models.dto.QuestionResponseDTO;
import com.dk.dermokometicapi.models.entities.Question;
import com.dk.dermokometicapi.models.entities.User;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuestionMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        // Configurar el mapeo para ignorar ciertos campos al convertir de Question a QuestionResponseDTO
        TypeMap<Question, QuestionResponseDTO> typeMap = modelMapper.createTypeMap(Question.class, QuestionResponseDTO.class);
        typeMap.addMappings(mapper -> {
            // Ignorar los campos "likes" y "answers"
            mapper.skip(QuestionResponseDTO::setLikes);
            mapper.skip(QuestionResponseDTO::setAnswers);
        });
    }

    public Question convertToEntity(QuestionRequestDTO questionRequestDTO) {
        Question q = modelMapper.map(questionRequestDTO, Question.class);
        q.setId(null);
        return q;
    }

    public QuestionResponseDTO convertToDTO(Question question, Long likes, Long answers) {
        QuestionResponseDTO questionResponseDTO = modelMapper.map(question, QuestionResponseDTO.class);
        questionResponseDTO.setLikes(likes);
        questionResponseDTO.setAnswers(answers);
        return questionResponseDTO;
    }
}
