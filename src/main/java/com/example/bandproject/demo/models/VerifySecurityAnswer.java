package com.example.bandproject.demo.models;

public class VerifySecurityAnswer {
    Long userId;
    String answer;

    public VerifySecurityAnswer(Long userId, String answer) {
        this.userId = userId;
        this.answer = answer;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
