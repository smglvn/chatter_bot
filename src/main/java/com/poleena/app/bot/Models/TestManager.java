package com.poleena.app.bot.Models;

import java.util.HashMap;
import java.util.Map;

public class TestManager {
    private Map<String, TestModel> tests;
    private String unknownTest;
    private TestModel chosenTest;
    private Map<String, String> testsNamesMap = new HashMap<>();

    public String testsList = "";

    public void init() {
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for (String key : tests.keySet()) {
            sb.append(i).append(": ").append(key).append("\n");
            testsNamesMap.put(Integer.toString(i), key);
            i++;
        }

        testsList = sb.toString();
    }

    public Response chooseTest(String test) {
        String testName = testsNamesMap.get(test);
        if (testName != null) {
            chosenTest = tests.get(testName);
            return new Response(
                    "Вы выбрали тест номер: " + test + " - " + testName + "\n" + chosenTest.firstQuestion().message,
                    1);
        }

        return new Response(unknownTest, 2);
    }

    public void reset() {
        if (chosenTest != null) {
            chosenTest.resetTest();
        }
    }

    public Response on(String input) {
        return chosenTest.nextQuestion(input);
    }
}
