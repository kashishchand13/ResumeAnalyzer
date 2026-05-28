package com.resumeanalyzer.nlp;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class TextCleaner {

    private static TokenizerME tokenizer;

    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
        "the", "and", "for", "with", "have", "has", "was", "are",
        "this", "that", "from", "will", "been", "not", "but", "our",
        "your", "their", "they", "which", "when", "where", "who",
        "how", "all", "can", "its", "into", "more", "also", "about",
        "than", "then", "some", "such", "each", "both", "over", "own"
    ));

    
    public static void init() throws IOException {
        InputStream modelIn = TextCleaner.class
                .getResourceAsStream("/models/en-token.bin");
        if (modelIn == null) {
            throw new IOException(
                "en-token.bin not found in src/main/resources/models/\n" +
                "Download it from: https://opennlp.apache.org/models.html"
            );
        }
        TokenizerModel model = new TokenizerModel(modelIn);
        tokenizer = new TokenizerME(model);
    }

   
    public static String clean(String rawText) {
        if (rawText == null || rawText.isEmpty()) return "";
        String text = rawText.toLowerCase();
        // Keep letters, digits, spaces, plus (+), hash (#), dot (.)
        text = text.replaceAll("[^a-z0-9\\s+#.]", " ");
        // Collapse multiple spaces into one
        text = text.replaceAll("\\s+", " ").trim();
        return text;
    }

   
    public static String[] tokenize(String cleanedText) {
        if (tokenizer == null) {
            throw new IllegalStateException(
                "TextCleaner not initialized. Call TextCleaner.init() first."
            );
        }
        return tokenizer.tokenize(cleanedText);
    }

   
    public static String[] removeStopwords(String[] tokens) {
        return Arrays.stream(tokens)
                .filter(t -> !STOPWORDS.contains(t) && t.length() > 1)
                .toArray(String[]::new);
    }
}
