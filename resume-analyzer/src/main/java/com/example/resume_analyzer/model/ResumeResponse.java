package com.example.resume_analyzer.model;

import java.util.List;

public class ResumeResponse {
    private int score;
    private List<String> missingSkills;

    public ResumeResponse(int score, List<String> missingSkills) {
        this.score = score;
        this.missingSkills = missingSkills;
    }

    public int getScore() {
        return score;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }
}