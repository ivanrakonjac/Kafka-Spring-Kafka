package com.springkafka;

public class ResultResponseModel {

    private String date;
    private int numOfUniqueUsers;

    public ResultResponseModel(String date, int numOfUniqueUsers) {
        this.date = date;
        this.numOfUniqueUsers = numOfUniqueUsers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumOfUniqueUsers() {
        return numOfUniqueUsers;
    }

    public void setNumOfUniqueUsers(int numOfUniqueUsers) {
        this.numOfUniqueUsers = numOfUniqueUsers;
    }
}
