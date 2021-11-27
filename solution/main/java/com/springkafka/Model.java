package com.springkafka;

public class Model {
    private String uid;
    private int ts;

    public Model(String uid, int ts) {
        this.uid = uid;
        this.ts = ts;
    }

    public Model() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }
}
