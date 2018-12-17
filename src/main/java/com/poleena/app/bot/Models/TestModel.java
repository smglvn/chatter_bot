package com.poleena.app.bot.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class TestModel {
    private ArrayList<String> questions;
    private Map<String, String> results;
    private Map<String, String> answers;
    private int questionIndex = 0;
    private String answerOptionsString;

    private TestManager testManager;

    private Map<String, Integer> answersGiven = new HashMap<>();

    String firstQuestion() {
        answers.keySet().forEach(answer -> answersGiven.put(answer, 0));
        answerOptionsString = String.join("\n", answers.values());

        return questions.get(++questionIndex) + "\n" + answerOptionsString;
    }

    void setTestManager(TestManager testManager) {
        this.testManager = testManager;
    }

    String nextQuestion(String answerPhrase) {
        if (!answers.containsKey(answerPhrase)) {
            return "Нет такого ответа( \n" + questions.get(questionIndex) + "\n" + answerOptionsString;
        }

        answersGiven.put(answerPhrase, answersGiven.get(answerPhrase) + 1);

        if (questionIndex == questions.size() - 1) {
            testManager.completeTest();
            String result = calculateResult();
            resetTest();
            return "Поздравляю, вы прошли тест. Вот ваш результат:\n" + result + "\n Пройдешь еще?";
        }

        return questions.get(++questionIndex) + "\n" + answerOptionsString;
    }

    void resetTest() {
        questionIndex = 0;
        answersGiven = new HashMap<>();
    }

    private String calculateResult() {
        String result = "";
        int maxCount = -1;
        for (String key : answersGiven.keySet())
        {
            int count = answersGiven.get(key);
            if (count > maxCount) {
                result = results.get(key);
                maxCount = count;
            }
        }

        return result;
    }
}
