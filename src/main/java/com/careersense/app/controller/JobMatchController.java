package com.careersense.app.controller;


import com.careersense.app.dto.JobDTO;
import com.careersense.app.dto.JobMatchResponse;
import com.careersense.app.service.JobScraperService;
import com.careersense.app.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/job-match")
@RequiredArgsConstructor
public class JobMatchController {

    private final ResumeService resumeService;
    private final JobScraperService jobScraperService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(
            @RequestParam("file") MultipartFile file) throws Exception {

        String text = resumeService.extractText(file);
        List<String> skills = resumeService.extractSkills(text);
        List<JobDTO> jobs = jobScraperService.findJobs(skills);

        return ResponseEntity.ok(
                new JobMatchResponse(skills, jobs)
        );
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<JobDTO>> getSuggestions(
            @RequestParam List<String> skills) {

        return ResponseEntity.ok(jobScraperService.findJobs(skills));
    }
}

