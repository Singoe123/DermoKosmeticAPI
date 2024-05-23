package com.dk.dermokometicapi.model.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequestDTO {
    @NotNull(message = "Content is mandatory")
    @NotBlank(message = "Content is mandatory")
    @Size(min = 1, max = 200, message = "Content must be between 1 and 200 characters")
    private String content;

    private Long parentAnswerId;

    @NotNull(message = "Question Id is mandatory")
    private Long questionId;

    @NotNull(message = "User Id is mandatory")
    private Long userId;
}
