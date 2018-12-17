package com.poleena.app.bot.FSM;

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
    private FSMBot fsm;


    String on(String phrase) {
        String symbol = symbols.get(phrase);

        if (phrase.equals("тест")) {
            fsm.setState("choosing");
            testManager.reset();
            return testManager.testsString;
        }

        if (symbol != null) {
            return transitions.get(symbol).on();
        }

        if (testing) {
            return testManager.on(phrase);
        }

        if (testChoosing) {
            return testManager.chooseTest(phrase);
        }

        return transitions.get("*").on();
    }

    void init(FSMBot fsm) {
        symbolsMap.keySet().forEach((symbol) -> {
            for (String phrase : symbolsMap.get(symbol)) {
                symbols.put(phrase, symbol);
            }
        });

        transitions.values().forEach(transition -> transition.init(fsm));

        testManager = fsm.testManager;
        this.fsm = fsm;
    }

}
