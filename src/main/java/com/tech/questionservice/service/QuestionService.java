package com.tech.questionservice.service;

import com.tech.questionservice.dao.QuestionRepo;
import com.tech.questionservice.model.Question;
import com.tech.questionservice.model.QuestionWrapper;
import com.tech.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        return new ResponseEntity<>(questionRepo.findByCategory(category), HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionRepo.save(question);
            return new ResponseEntity<>("Successfully added question", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> updateQuestion(Question question) {
        Optional<Question> questionOptional = questionRepo.findById(question.getId());
        if (questionOptional.isPresent()) {
            Question questionToUpdate = questionOptional.get();
            questionToUpdate.setQuestion_title(question.getQuestion_title());
            questionRepo.save(question);
            return new ResponseEntity<>("Successfully updated question", HttpStatus.OK);
        }

        return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Integer>> generateQuestions(String category, int numQ) {
        List<Integer> qIds = questionRepo.findRandomQuestionsByCategory(category, numQ);

        return new ResponseEntity<>(qIds, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuiz(List<Integer> qIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        for(Integer qId : qIds) {
            Question q = questionRepo.findById(qId).get();
            QuestionWrapper qw = new QuestionWrapper(q.getId(),
                    q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4(), q.getQuestion_title());
            wrappers.add(qw);
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScoreForResponses(List<Response> responses) {
        int result = 0;
        for(Response r: responses) {
            Question question = questionRepo.findById(r.getId()).get();

            if(question.getRight_answer().equals(r.getResponse())) {
                result++;
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
