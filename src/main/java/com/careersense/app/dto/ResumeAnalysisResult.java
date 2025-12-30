package com.careersense.app.dto;

import java.util.List;

public record ResumeAnalysisResult(
        int score,
        boolean atsFriendly,
        String experienceLevel,
        List<String> matchedSkills,
        List<String> missingSkills,
        List<String> suggestedRoles,
        List<String> ruleBasedSuggestions,
        String aiSuggestions
) {}
