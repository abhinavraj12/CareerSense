package com.careersense.app.service;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    private static final List<String> SKILL_DB = List.of(
            "Java", "Spring Boot", "React", "Node.js",
            "MongoDB", "SQL", "AWS", "Docker",
            "Python", "Machine Learning"
    );

    public String extractText(MultipartFile file) throws Exception {
        Tika tika = new Tika();
        return tika.parseToString(file.getInputStream());
    }

    public List<String> extractSkills(String text) {
        String lower = text.toLowerCase();

        return SKILL_DB.stream()
                .filter(skill -> lower.contains(skill.toLowerCase()))
                .collect(Collectors.toList());
    }
}