package com.counting_mechanism;

import java.util.Date;
import java.util.HashSet;

public class Second {

    private int ts;
    private HashSet<String> userIdsHashSet;
    private Date dateTs;

    public Second() {
        userIdsHashSet = new HashSet<String>();
    }

    public Second(int ts, String userId) {
        this.ts = ts;
        userIdsHashSet = new HashSet<String>();
        userIdsHashSet.add(userId);
        dateTs = new Date((long) ts * 1000);
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public HashSet<String> getUserIdsHashSet() {
        return userIdsHashSet;
    }

    public void setUserIdsHashSet(HashSet<String> userIdsHashSet) {
        this.userIdsHashSet = userIdsHashSet;
    }

    public boolean userIdExists(String userId){
        return userIdsHashSet.contains(userId);
    }

    public void addUserId(String userId){
        userIdsHashSet.add(userId);
    }

    public Date getDateTs() {
        return dateTs;
    }

    public void setDateTs(Date dateTs) {
        this.dateTs = dateTs;
    }
}
