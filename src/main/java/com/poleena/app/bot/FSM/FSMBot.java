package com.poleena.app.bot.FSM;

import com.poleena.app.bot.Models.TestManager;

import java.util.Map;

public class FSMBot {

    private String currentState;
    private Map<String, State> states;
    private String help;
    TestManager testManager;

    public String process(String phrase) {
        return phrase.equals("help") ? help : states.get(currentState).on(phrase);
    }

    public void setState(String state) {
        currentState = state;
    }

    void init(TestManager tester) {
        this.testManager = tester;
        states.values().forEach((state) -> state.init(this));
    }
}
