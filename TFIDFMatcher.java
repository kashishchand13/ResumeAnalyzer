package com.resumeanalyzer.nlp;

import java.util.*;


public class TFIDFMatcher {

   
    private Map<String, Double> computeTF(String[] tokens) {
        Map<String, Double> tf = new HashMap<>();
        for (String token : tokens) {
            tf.put(token, tf.getOrDefault(token, 0.0) + 1.0);
        }
        int total = tokens.length;
        if (total > 0) {
            tf.replaceAll((k, v) -> v / total);
        }
        return tf;
    }

  
    private Map<String, Double> computeIDF(Map<String, Double> tf1,
                                            Map<String, Double> tf2) {
        Map<String, Double> idf = new HashMap<>();
        Set<String> vocab = new HashSet<>();
        vocab.addAll(tf1.keySet());
        vocab.addAll(tf2.keySet());

        int totalDocs = 2; // we always compare exactly 2 documents

        for (String word : vocab) {
            int docsWithWord = 0;
            if (tf1.containsKey(word)) docsWithWord++;
            if (tf2.containsKey(word)) docsWithWord++;
            // +1 smoothing prevents division by zero
            idf.put(word, Math.log((double) totalDocs / (docsWithWord + 1)) + 1.0);
        }
        return idf;
    }

   
    private Map<String, Double> computeTFIDF(Map<String, Double> tf,
                                              Map<String, Double> idf) {
        Map<String, Double> tfidf = new HashMap<>();
        for (Map.Entry<String, Double> entry : tf.entrySet()) {
            tfidf.put(entry.getKey(), entry.getValue() * idf.get(entry.getKey()));
        }
        return tfidf;
    }

    
    private double cosineSimilarity(Map<String, Double> vec1,
                                     Map<String, Double> vec2) {
        Set<String> vocab = new HashSet<>();
        vocab.addAll(vec1.keySet());
        vocab.addAll(vec2.keySet());

        double dotProduct = 0.0;
        double mag1 = 0.0;
        double mag2 = 0.0;

        for (String word : vocab) {
            double v1 = vec1.getOrDefault(word, 0.0);
            double v2 = vec2.getOrDefault(word, 0.0);
            dotProduct += v1 * v2;
            mag1 += v1 * v1;
            mag2 += v2 * v2;
        }

        if (mag1 == 0 || mag2 == 0) return 0.0;

        double similarity = dotProduct / (Math.sqrt(mag1) * Math.sqrt(mag2));
        // Round to 2 decimal places and scale to 0-100
        return Math.round(similarity * 10000.0) / 100.0;
    }

    
    public double computeSimilarity(String[] resumeTokens, String[] jobTokens) {
        Map<String, Double> tf1 = computeTF(resumeTokens);
        Map<String, Double> tf2 = computeTF(jobTokens);
        Map<String, Double> idf  = computeIDF(tf1, tf2);

        Map<String, Double> tfidf1 = computeTFIDF(tf1, idf);
        Map<String, Double> tfidf2 = computeTFIDF(tf2, idf);

        return cosineSimilarity(tfidf1, tfidf2);
    }
}
