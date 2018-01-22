package com.ala.elearning.Beans;

/**
 * Created by alaam on 11/30/2017.
 */

public class Choice {
    public final static int STATUS_CORRECT = 1;
    public final static int STATUS_INCORRECT = 0;
    private int id;
    private int status;

    public boolean isCorrect(){
        return status == 1;
    }
    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    private int qid;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
