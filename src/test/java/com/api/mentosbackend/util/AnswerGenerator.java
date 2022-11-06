package com.api.mentosbackend.util;

import com.api.mentosbackend.entities.Answer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.api.mentosbackend.util.QuestionGenerator.question;
import static com.api.mentosbackend.util.UserGenerator.user;

public class AnswerGenerator {

    public static Answer answer(Long answerId, Long userId, Long questionId){
        return new Answer(answerId, "Answer description", new Date(), user(userId), question(questionId));
    }

    public static Answer answer(Long id) { return answer(id, 1L, 1L); }

    public static Answer answer(){ return answer(1L); }

    public static List<Answer> answers(int size) {
        List<Answer> answers = new ArrayList<>();
        for (long i = 0L; i < size;  ++i)
            answers.add(answer(i, i + 1, i + 1));
        return answers;
    }

    public static List<Answer> answers() { return answers(10); }
}
