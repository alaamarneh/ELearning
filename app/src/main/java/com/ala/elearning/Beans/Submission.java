package com.ala.elearning.Beans;

/**
 * Created by alaam on 12/1/2017.
 */

public class Submission {
    private int id,cid,q_id;
    private String uid;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    private String uname;

    public int getQ_id() {
        return q_id;
    }

    public void setQ_id(int q_id) {
        this.q_id = q_id;
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
                ", 'q_id':" + q_id +
                ", 'uname':" + uname +
                '}';
    }
}
