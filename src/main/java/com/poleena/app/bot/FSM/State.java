package com.poleena.app.bot.FSM;

import java.util.Map;

public class State {
    private Map<String, Transition> transitions;

    public String on(String symbol){
        return transitions.get(symbol).on();
    }
}
