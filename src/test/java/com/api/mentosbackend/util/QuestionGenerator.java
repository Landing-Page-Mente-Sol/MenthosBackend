package com.api.mentosbackend.util;

import com.api.mentosbackend.entities.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.api.mentosbackend.util.CourseGenerator.*;
import static com.api.mentosbackend.util.CustomerGenerator.*;


public class QuestionGenerator {


    public  static Question question(){ return question(1L); }
    public static Question question(Long id) { return question(id, 1L, 1L); }
    public static Question question(Long questionId, Long customerId, Long courseId){
        return new Question(questionId, "Question description", "Question title", new Date(), customer(customerId), course(courseId));
    }

    public static List<Question> questions(int size) {
        List<Question> questions = new ArrayList<>();
        for(long i = 0L; i < size; ++i)
            questions.add(question(i, i + 1, i + 1));

        return questions;
    }

    public static List<Question> questions(){ return questions(10); }
}
