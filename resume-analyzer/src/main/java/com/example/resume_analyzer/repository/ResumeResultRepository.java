package com.example.resume_analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.resume_analyzer.model.ResumeResult;

public interface ResumeResultRepository extends JpaRepository<ResumeResult, Integer> {
}