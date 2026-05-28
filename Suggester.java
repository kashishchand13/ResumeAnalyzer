package com.resumeanalyzer.nlp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Suggester {

    
    public static List<String> suggest(List<String> resumeSkills,
                                        List<String> jobSkills) {
        Set<String> resumeSet = new HashSet<>(resumeSkills);
        List<String> suggestions = new ArrayList<>();

        for (String skill : jobSkills) {
            if (!resumeSet.contains(skill)) {
                suggestions.add("Consider adding: \"" + skill + "\"");
            }
        }

        return suggestions;
    }

    
    public static List<String> getMissingSkills(List<String> resumeSkills,
                                                  List<String> jobSkills) {
        Set<String> resumeSet = new HashSet<>(resumeSkills);
        List<String> missing = new ArrayList<>();

        for (String skill : jobSkills) {
            if (!resumeSet.contains(skill)) {
                missing.add(skill);
            }
        }

        return missing;
    }
}
