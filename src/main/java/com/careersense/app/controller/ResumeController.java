package com.careersense.app.controller;

import com.careersense.app.dto.ResumeAnalysisResult;
import com.careersense.app.service.ResumeAnalysisService;
import com.careersense.app.util.ResumeScoringUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume-score")
public class ResumeController {

    private final ResumeAnalysisService resumeAnalysisService;

    public ResumeController(ResumeAnalysisService resumeAnalysisService) {
        this.resumeAnalysisService = resumeAnalysisService;
    }

    @PostMapping("/analyze")
    public ResumeAnalysisResult analyze(
            @RequestParam("resume") MultipartFile resume,
            @RequestParam("experience") int experience
    ) throws Exception {
        return resumeAnalysisService.analyzeResume(resume, experience);
    }
}

