package com.example.bandproject.demo.models;

public class AddMembership {
    Long memberId;
    Long bandId;

    public AddMembership(Long memberId, Long bandId) {
        this.memberId = memberId;
        this.bandId = bandId;
    }

    public AddMembership() {
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getBandId() {
        return bandId;
    }

    public void setBandId(Long bandId) {
        this.bandId = bandId;
    }
}
