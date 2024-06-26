package com.dk.dermokometicapi.controllers;


import com.dk.dermokometicapi.models.dto.*;

import com.dk.dermokometicapi.services.QuestionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


    @GetMapping
    public ResponseEntity<List<QuestionResponseDTO>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }


    @Transactional
    @PostMapping
    public ResponseEntity<QuestionResponseDTO> createQuestion(@Validated @RequestBody QuestionRequestDTO questionRequestDTO) {
        return ResponseEntity.ok(questionService.createQuestion(questionRequestDTO));
    }


    @Transactional
    @GetMapping("/id/{id}")
    public ResponseEntity<QuestionResponseDTO> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @Transactional
    @GetMapping("/title/{title}")
    public ResponseEntity<QuestionResponseDTO> getQuestionByTitle(@PathVariable String title) {
        return ResponseEntity.ok(questionService.getQuestionByTitle(title));
    }

    @Transactional
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteQuestionById(@PathVariable Long id) {
        questionService.deleteQuestionById(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PostMapping("/like")
    public ResponseEntity<QuestionLikeResponseDTO> likeQuestion(@RequestBody QuestionLikeRequestDTO questionLikeRequestDTO) {
        return ResponseEntity.ok(questionService.createLike(questionLikeRequestDTO));
    }

    @Transactional
    @DeleteMapping("/like")
    public ResponseEntity<Void> deleteLikeQuestion(@RequestBody QuestionLikeRequestDTO questionLikeRequestDTO) {
        questionService.deleteLike(questionLikeRequestDTO);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @PostMapping("/filter")
    public ResponseEntity<Page<QuestionResponseDTO>> searchQuestions(@RequestBody FilterRequestDTO filterRequestDTO) {
        return ResponseEntity.ok(questionService.getFilteredList(filterRequestDTO));
    }
}
