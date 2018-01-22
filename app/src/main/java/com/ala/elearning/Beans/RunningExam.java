package com.ala.elearning.Beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 12/31/2017.
 */

public class RunningExam {
    private Exam exam;
    private String paragraph,title;
    private List<Ques> questions;

    public RunningExam() {
    }


    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Ques> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Ques> questions) {
        this.questions = questions;
    }
}
