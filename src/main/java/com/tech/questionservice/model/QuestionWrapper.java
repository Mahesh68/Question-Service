package com.tech.questionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionWrapper {
    private Integer id;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String question_title;
}