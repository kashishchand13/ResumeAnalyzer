package com.example.resume_analyzer.model;

import jakarta.persistence.*;

@Entity
public class ResumeResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
    private double matchScore;

    @Column(length = 1000)
    private String missingSkills;

    // 🔹 Getter for id
    public int getId() {
        return id;
    }

    // 🔹 Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // 🔹 Getter for userId
    public int getUserId() {
        return userId;
    }

    // 🔹 Setter for userId
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // 🔹 Getter for matchScore
    public double getMatchScore() {
        return matchScore;
    }

    // 🔹 Setter for matchScore
    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }

    // 🔹 Getter for missingSkills
    public String getMissingSkills() {
        return missingSkills;
    }

    // 🔹 Setter for missingSkills
    public void setMissingSkills(String missingSkills) {
        this.missingSkills = missingSkills;
    }
}