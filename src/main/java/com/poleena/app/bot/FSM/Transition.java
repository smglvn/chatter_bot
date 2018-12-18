package com.poleena.app.bot.FSM;

import java.util.concurrent.ThreadLocalRandom;

class Transition {
    private String[] messages;
    String nextState;

    Transition(String message, String nextState) {
        this.messages = new String[]{message};
        this.nextState = nextState;
    }

    Transition(String message) {
        this.messages = new String[]{message};
    }

    String getTransitionMessage(){
        return messages[ThreadLocalRandom.current().nextInt(messages.length)];
    }
}
