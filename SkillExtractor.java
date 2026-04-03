package com.resumeanalyzer.nlp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class SkillExtractor {

    private final Set<String> skillsDatabase;

    
    private static final Map<String, String> SYNONYMS = new HashMap<>();
    static {
        SYNONYMS.put("ml",  "machine learning");
        SYNONYMS.put("ai",  "artificial intelligence");
        SYNONYMS.put("nlp", "natural language processing");
        SYNONYMS.put("dl",  "deep learning");
        SYNONYMS.put("js",  "javascript");
        SYNONYMS.put("ts",  "typescript");
        SYNONYMS.put("db",  "database");
        SYNONYMS.put("oop", "object oriented programming");
        SYNONYMS.put("ds",  "data structures");
    }

    public SkillExtractor(String skillsFilePath) throws IOException {
        skillsDatabase = new HashSet<>();
        List<String> lines = Files.readAllLines(Paths.get(skillsFilePath));
        for (String line : lines) {
            String skill = line.trim().toLowerCase();
            if (!skill.isEmpty() && !skill.startsWith("//")) {
                skillsDatabase.add(skill);
            }
        }
    }

    
    public List<String> extract(String cleanedText) {
        List<String> found = new ArrayList<>();
        String[] words = cleanedText.split("\\s+");

        // Expand synonyms first
        for (int i = 0; i < words.length; i++) {
            if (SYNONYMS.containsKey(words[i])) {
                words[i] = SYNONYMS.get(words[i]);
            }
        }

        // Single-word skills
        for (String word : words) {
            if (skillsDatabase.contains(word) && !found.contains(word)) {
                found.add(word);
            }
        }

        // Two-word skills (bigrams): "machine learning", "data science"
        for (int i = 0; i < words.length - 1; i++) {
            String bigram = words[i] + " " + words[i + 1];
            if (skillsDatabase.contains(bigram) && !found.contains(bigram)) {
                found.add(bigram);
            }
        }

        // Three-word skills (trigrams): "natural language processing"
        for (int i = 0; i < words.length - 2; i++) {
            String trigram = words[i] + " " + words[i + 1] + " " + words[i + 2];
            if (skillsDatabase.contains(trigram) && !found.contains(trigram)) {
                found.add(trigram);
            }
        }

        return found;
    }
}
