package com.resumeanalyzer;

import com.resumeanalyzer.nlp.*;

import java.util.List;


public class Main {

    public static void main(String[] args) throws Exception {

        // ── CONFIGURATION ──────────────────────────────────────────────
        // Change these paths to point to your actual files
        String resumePath      = "src/main/resources/resume.pdf";
        String jobDescPath     = "src/main/resources/job_description.txt";
        String skillsListPath  = "src/main/resources/skills_list.txt";
        // ───────────────────────────────────────────────────────────────

        System.out.println("========================================");
        System.out.println("       RESUME ANALYZER — NLP MODULE     ");
        System.out.println("========================================\n");

        // STEP 0 — Initialize OpenNLP (MUST be done once before tokenizing)
        System.out.println("[1/6] Initializing NLP models...");
        TextCleaner.init();

        // STEP 1 — Read files
        System.out.println("[2/6] Reading resume and job description...");
        String resumeRaw  = PDFExtractor.extractFromPDF(resumePath);
        String jobDescRaw = PDFExtractor.extractFromTxt(jobDescPath);

        // STEP 2 — Clean text
        System.out.println("[3/6] Cleaning text...");
        String resumeClean  = TextCleaner.clean(resumeRaw);
        String jobDescClean = TextCleaner.clean(jobDescRaw);

        // STEP 3 — Tokenize and remove stopwords
        System.out.println("[4/6] Tokenizing...");
        String[] resumeTokens = TextCleaner.removeStopwords(
                                    TextCleaner.tokenize(resumeClean));
        String[] jobTokens    = TextCleaner.removeStopwords(
                                    TextCleaner.tokenize(jobDescClean));

        // STEP 4 — Extract skills
        System.out.println("[5/6] Extracting skills...");
        SkillExtractor extractor = new SkillExtractor(skillsListPath);
        List<String> resumeSkills = extractor.extract(resumeClean);
        List<String> jobSkills    = extractor.extract(jobDescClean);

        // STEP 5 — Compute TF-IDF similarity
        System.out.println("[6/6] Computing similarity and score...\n");
        TFIDFMatcher matcher      = new TFIDFMatcher();
        double similarityScore    = matcher.computeSimilarity(resumeTokens, jobTokens);

        // STEP 6 — Final score
        double finalScore = Scorer.score(similarityScore, resumeSkills, jobSkills);
        String scoreLabel = Scorer.getLabel(finalScore);

        // STEP 7 — Suggestions
        List<String> suggestions  = Suggester.suggest(resumeSkills, jobSkills);
        List<String> missingSkills = Suggester.getMissingSkills(resumeSkills, jobSkills);

        // ── PRINT RESULTS ──────────────────────────────────────────────
        System.out.println("========================================");
        System.out.println("               RESULTS                  ");
        System.out.println("========================================");
        System.out.printf("  Final Score     : %.2f / 100%n", finalScore);
        System.out.printf("  Rating          : %s%n", scoreLabel);
        System.out.printf("  Text Similarity : %.2f%%%n", similarityScore);
        System.out.println("----------------------------------------");
        System.out.println("  Skills in Resume : " + resumeSkills);
        System.out.println("  Skills in Job    : " + jobSkills);
        System.out.println("  Missing Skills   : " + missingSkills);
        System.out.println("----------------------------------------");
        System.out.println("  SUGGESTIONS:");
        if (suggestions.isEmpty()) {
            System.out.println("  Great! Your resume covers all required skills.");
        } else {
            for (String s : suggestions) {
                System.out.println("   -> " + s);
            }
        }
        System.out.println("========================================");
    }
}
