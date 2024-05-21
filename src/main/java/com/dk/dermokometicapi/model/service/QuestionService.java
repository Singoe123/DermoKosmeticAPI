package com.dk.dermokometicapi.model.service;

import com.dk.dermokometicapi.model.dto.*;
import com.dk.dermokometicapi.model.entity.Question;
import com.dk.dermokometicapi.model.entity.QuestionLike;
import com.dk.dermokometicapi.model.entity.User;
import com.dk.dermokometicapi.model.exception.BadRequestException;
import com.dk.dermokometicapi.model.exception.ResourceNotFoundException;
import com.dk.dermokometicapi.model.mapper.ArticleMapper;
import com.dk.dermokometicapi.model.mapper.QuestionLikeMapper;
import com.dk.dermokometicapi.model.mapper.QuestionMapper;
import com.dk.dermokometicapi.model.repository.ArticleRepository;
import com.dk.dermokometicapi.model.repository.QuestionLikeRepository;
import com.dk.dermokometicapi.model.repository.QuestionRepository;
import com.dk.dermokometicapi.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuestionLikeRepository questionLikeRepository;
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UserRepository userRepository;
    private final QuestionLikeMapper questionLikeMapper;


    //create question
    public QuestionResponseDTO createQuestion(QuestionRequestDTO questionRequestDTO) {
        if (questionRepository.existsByTitle(questionRequestDTO.getTitle())) {
            throw new BadRequestException("Question with title: " + questionRequestDTO.getTitle() + " already exists");
        }
        Question newQuestion = questionMapper.convertToEntity(questionRequestDTO);
        newQuestion.setPublicationDate(LocalDate.now());
        questionRepository.save(newQuestion);
        return questionMapper.convertToDTO(newQuestion, 0L, 0L);
    }

    private List<QuestionResponseDTO> getQuestionListDTO(List<Question> questions) {
        return questions.stream()
                .map(question -> {
                    Long likes = questionRepository.findQuestionLikesById(question.getId());
                    Long answers = questionRepository.findQuestionAnswersById(question.getId());
                    System.out.println(likes +  answers);
                    return questionMapper.convertToDTO(question, likes, answers);
                })
                .collect(Collectors.toList());
    }

    //get all articles
    public List<QuestionResponseDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return getQuestionListDTO(questions);
    }

}