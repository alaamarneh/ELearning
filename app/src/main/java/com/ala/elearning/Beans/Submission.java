package com.ala.elearning.Beans;

/**
 * Created by alaam on 12/1/2017.
 */

public class Submission {
    private int id,cid,qid,exam_id;
    private String uid;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    private String uname;

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "{" +
                "'id':" + id +
                ", 'cid':" + cid +
                ", 'uid':" + uid +
                ", 'qid':" + qid +
                ", 'exam_id':" + exam_id +
                '}';
    }
}
