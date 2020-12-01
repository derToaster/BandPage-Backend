package com.example.bandproject.demo.models;

public class AddSkill {
    Long instrumentId;
    Long userId;
    Long skillLevelId;

    public AddSkill(Long instrumentId, Long userId, Long skillLevelId) {
        this.instrumentId = instrumentId;
        this.userId = userId;
        this.skillLevelId = skillLevelId;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSkillLevelId() {
        return skillLevelId;
    }

    public void setSkillLevelId(Long skillLevelId) {
        this.skillLevelId = skillLevelId;
    }
}
