package com.poleena.app.bot.FSM;

import com.poleena.app.bot.Models.TestManager;

import java.util.Map;

class FSMBot {
    private String currentState;
    private Map<String, State> states;
    private String help;

    String process(String phrase) {
        Transition transition = states.get(currentState).on(phrase);
        setState(transition.nextState);

        return phrase.equals("help") ? help : transition.getTransitionMessage();
    }

    private void setState(String state) {
        if (state != null) {
            currentState = state;
        }
    }

    void initStates(TestManager testManager) {
        states.values().forEach((state) -> state.init(testManager));
    }
}
