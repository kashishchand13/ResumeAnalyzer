package com.resumeanalyzer.nlp;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class PDFExtractor {

    
    public static String extractFromPDF(String pdfPath) throws IOException {
        File file = new File(pdfPath);
        if (!file.exists()) {
            throw new IOException("File not found: " + pdfPath);
        }
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            // Sort by position so text reads top-to-bottom, left-to-right
            stripper.setSortByPosition(true);
            return stripper.getText(document);
        }
    }

   
    public static String extractFromTxt(String txtPath) throws IOException {
        File file = new File(txtPath);
        if (!file.exists()) {
            throw new IOException("File not found: " + txtPath);
        }
        return new String(Files.readAllBytes(Paths.get(txtPath)));
    }
}
