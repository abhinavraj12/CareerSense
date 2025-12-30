package com.careersense.app.service;


import com.careersense.app.dto.JobDTO;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobScraperService {

    public List<JobDTO> findJobs(List<String> skills) {
        String query = String.join(" ", skills);
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        List<JobDTO> jobs = new ArrayList<>();

        // 🔹 Naukri
        jobs.add(new JobDTO(
                query + " Developer",
                "Multiple Companies",
                "India",
                "https://www.naukri.com/" + encodedQuery + "-jobs",
                "Naukri"
        ));

        // 🔹 LinkedIn
        jobs.add(new JobDTO(
                query + " Engineer",
                "Multiple Companies",
                "Remote",
                "https://www.linkedin.com/jobs/search/?keywords=" + encodedQuery,
                "LinkedIn"
        ));

        // 🔹 Indeed
        jobs.add(new JobDTO(
                query + " Specialist",
                "Multiple Companies",
                "Worldwide",
                "https://www.indeed.com/jobs?q=" + encodedQuery,
                "Indeed"
        ));

        // 🔹 Google Jobs
        jobs.add(new JobDTO(
                query + " Role",
                "Multiple Companies",
                "Anywhere",
                "https://www.google.com/search?q=" + encodedQuery + "+jobs",
                "Google Jobs"
        ));

        return jobs;
    }
}

