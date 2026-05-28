package com.example.resume_analyzer.service;

import com.example.resume_analyzer.model.ResumeRequest;
import com.example.resume_analyzer.model.ResumeResponse;

import java.util.*;

public class ResumeService {

    public ResumeResponse analyze(ResumeRequest request) {

       String[] resumeWords = request.getResumeText()
        .toLowerCase()
        .replace(",", "")
        .split("\\s+");

       String[] jobWords = request.getJobDescription()
        .toLowerCase()
        .replace(",", "")
        .split("\\s+");

        Set<String> resumeSet = new HashSet<>(Arrays.asList(resumeWords));
        Set<String> jobSet = new HashSet<>(Arrays.asList(jobWords));

        List<String> missing = new ArrayList<>();

        int matchCount = 0;

        for (String word : jobSet) {
            if (resumeSet.contains(word)) {
                matchCount++;
            } else {
                missing.add(word);
            }
        }

        int score = (matchCount * 100) / jobSet.size();

        return new ResumeResponse(score, missing);
    }
}