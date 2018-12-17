package com.poleena.app.bot.FSM;

import java.util.concurrent.ThreadLocalRandom;

class Transition {
    private String[] messages;
    private boolean tests = false;
    private String nextState;
    private FSMBot fsm;

    void init(FSMBot fsm) {
        this.fsm = fsm;
    }

    String on(){
        fsm.setState(nextState);
        if (tests) {
            return fsm.testManager.testsString;
        }

        return messages[ThreadLocalRandom.current().nextInt(messages.length)];
    }
}
