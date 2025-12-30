package com.careersense.app.util;

import java.util.*;

public class ResumeScoringUtil {

    public record Result(
            int score,
            boolean atsFriendly,
            String experienceLevel,
            List<String> matchedSkills,
            List<String> missingSkills,
            List<String> suggestedRoles,
            List<String> suggestions
    ) {}

    public static Result analyze(String text, String filename, int years) {

        String content = text.toLowerCase();
        List<String> suggestions = new ArrayList<>();
        List<String> matched = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        // ---- DOMAIN DETECTION ----
        String domain = DomainDetector.detectDomain(content);
        List<String> domainSkills =
                SkillOntology.DOMAIN_SKILLS.getOrDefault(domain, List.of());

        // ---- SKILL SCORING (60%) ----
        int totalWords = Math.max(content.split("\\s+").length, 1);
        int densityScore = 0;

        for (String skill : domainSkills) {
            int count = countOccurrences(content, skill);
            if (count > 0) {
                matched.add(skill);
                densityScore += 4;
            } else {
                missing.add(skill);
            }
        }

        int skillScore = Math.min(densityScore, 60);

        // ---- SECTION SCORE (25%) ----
        int sectionScore = 0;
        if (content.contains("experience")) sectionScore += 6;
        if (content.contains("skills")) sectionScore += 6;
        if (content.contains("education")) sectionScore += 6;
        if (content.contains("projects")) sectionScore += 7;

        // ---- ATS SCORE (15%) ----
        boolean atsFriendly =
                filename != null &&
                        (filename.endsWith(".pdf") || filename.endsWith(".docx"));

        int atsScore = atsFriendly ? 15 : 0;

        if (!atsFriendly) {
            suggestions.add("Use PDF or DOCX format for ATS compatibility");
        }

        // ---- SUGGESTIONS ----
        if (!missing.isEmpty()) {
            suggestions.add("Consider learning: " +
                    String.join(", ", missing.subList(0, Math.min(5, missing.size()))));
        }

        // ---- ROLE SUGGESTIONS ----
        List<String> roles = SkillOntology.ROLE_MAPPING
                .getOrDefault(domain, List.of("General Professional"));

        // ---- FINAL SCORE ----
        int finalScore = Math.min(skillScore + sectionScore + atsScore, 100);

        return new Result(
                finalScore,
                atsFriendly,
                experienceLabel(years),
                matched,
                missing,
                roles,
                suggestions
        );
    }

    private static int countOccurrences(String text, String word) {
        int count = 0, idx = text.indexOf(word);
        while (idx != -1) {
            count++;
            idx = text.indexOf(word, idx + word.length());
        }
        return count;
    }

    private static String experienceLabel(int years) {
        if (years <= 1) return "ENTRY / FRESHER";
        if (years <= 2) return "JUNIOR";
        if (years <= 5) return "MID-LEVEL";
        return "SENIOR";
    }
}
