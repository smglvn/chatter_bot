package com.poleena.app.bot.Models;

import com.poleena.app.bot.FSM.FSMBot;

import java.util.HashMap;
import java.util.Map;

public class TestManager {
    private Map<String, TestModel> tests;
    private String unknownTestAnswer;
    private TestModel chosenTest;
    private FSMBot fsmBot;
    private Map<String, String> testsNamesMap = new HashMap<>();

    public String testsString = "";

    public void init() {
        int i = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("Напишите мне цифру теста, который хотите пройти\n");
        for (String key : tests.keySet())
        {
            sb.append(i).append(": ").append(key).append("\n");
            testsNamesMap.put(Integer.toString(i), key);
            i++;
        }

        tests.values().forEach(test -> test.setTestManager(this));
        testsString = sb.toString();
    }

    public void setFsmBot(FSMBot fsmBot) {
        this.fsmBot = fsmBot;
    }

    void completeTest() {
        fsmBot.process("finish");
    }

    public String chooseTest(String test) {
        String testName = testsNamesMap.get(test);
        if (testName != null) {
            fsmBot.setState("testing");
            chosenTest = tests.get(testName);
            return "Вы выбрали тест номер: " + test + " - " + testName + "\n"+ chosenTest.firstQuestion();
        }

        return unknownTestAnswer;
    }

    public void reset() {
        if (chosenTest != null) {
            chosenTest.resetTest();
        }
    }

    public String on(String input) {
        return chosenTest.nextQuestion(input);
    }
}
