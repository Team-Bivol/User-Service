package com.example.user;

public class UserInterestRequest {
    private String userId;

    private int interestId;

    public UserInterestRequest(String userId, int interestId) {
        this.userId = userId;
        this.interestId = interestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getInterestId() {
        return interestId;
    }

    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }
}
