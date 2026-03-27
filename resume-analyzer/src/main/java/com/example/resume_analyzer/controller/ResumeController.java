package com.example.resume_analyzer.controller;

import org.springframework.web.bind.annotation.*;
import com.example.resume_analyzer.service.ResumeService;
import com.example.resume_analyzer.model.ResumeRequest;
import com.example.resume_analyzer.model.ResumeResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin   // IMPORTANT

public class ResumeController {

    ResumeService service = new ResumeService();

    @PostMapping("/analyze")
    public ResumeResponse analyze(@RequestBody ResumeRequest request) {
        return service.analyze(request);
    }
}