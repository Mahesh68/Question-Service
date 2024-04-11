package com.tech.questionservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String question_title;

    private String right_answer;

    private String level;

    private String category;
}