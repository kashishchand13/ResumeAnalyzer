package com.resumeanalyzer.nlp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Scorer {

    private static final double SIMILARITY_WEIGHT = 0.6;
    private static final double KEYWORD_WEIGHT    = 0.4;

    
    public static double score(double similarityScore,
                                List<String> resumeSkills,
                                List<String> jobSkills) {

        // Keyword match: what % of job skills appear in resume
        double keywordScore = 0.0;
        if (!jobSkills.isEmpty()) {
            Set<String> resumeSet = new HashSet<>(resumeSkills);
            long matched = jobSkills.stream()
                    .filter(resumeSet::contains)
                    .count();
            keywordScore = (double) matched / jobSkills.size() * 100.0;
        }

        // Weighted combination
        double finalScore = (SIMILARITY_WEIGHT * similarityScore)
                          + (KEYWORD_WEIGHT    * keywordScore);

        // Round to 2 decimal places
        return Math.round(finalScore * 100.0) / 100.0;
    }

   
    public static String getLabel(double score) {
        if (score >= 80) return "Excellent Match";
        if (score >= 60) return "Good Match";
        if (score >= 40) return "Moderate Match";
        return "Poor Match — consider updating your resume";
    }
}
