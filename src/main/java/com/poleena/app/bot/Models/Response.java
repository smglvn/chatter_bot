package com.poleena.app.bot.Models;

/**
 * Status = 0 test is finished
 * Status = 1 correct answer is given and test is not complete yet
 * Status = 2 unknown test
 * Status = 3 unknown test answer
 */
public class Response {
    public String message;
    public int status;

    Response(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
