package com.poleena.app.bot.FSM;

import com.poleena.app.bot.Models.Response;
import com.poleena.app.bot.Models.TestManager;

import java.util.HashMap;
import java.util.Map;

class State {
    private boolean testing = false;
    private boolean testChoosing = false;
    private Map<String, Transition> transitions;
    private Map<String, String[]> symbolsMap;

    private Map<String, String> symbols = new HashMap<>();
    private TestManager testManager;


    Transition on(String phrase) {
        String symbol = symbols.get(phrase);

        if (phrase.equals("тест")) {
            testManager.reset();
            return new Transition("Выберите цифру понравившегося теста\n" + testManager.testsList, "choosing");
        }

        if (symbol != null) {
            return transitions.get(symbol);
        }

        if (testing) {
            Response response = testManager.on(phrase);
            Transition transition;

            if (response.status == 0) {
                transition = new Transition(response.message + "\nПройдешь еще?\n" + testManager.testsList, "choosing");
            } else {
                transition = new Transition(response.message);
            }

            return transition;
        }

        if (testChoosing) {
            Response response = testManager.chooseTest(phrase);
            Transition transition;

            if (response.status == 1) {
                transition = new Transition(response.message, "testing");
            } else {
                transition = new Transition(response.message);
            }
            return transition;
        }

        return transitions.get("*");
    }

    void init(TestManager testManager) {
        symbolsMap.keySet().forEach((symbol) -> {
            for (String phrase : symbolsMap.get(symbol)) {
                symbols.put(phrase, symbol);
            }
        });

        this.testManager = testManager;
    }

}
