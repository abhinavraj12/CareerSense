package com.careersense.app.util;

import java.util.*;

public class DomainDetector {

    public static String detectDomain(String content) {
        content = content.toLowerCase();

        String bestDomain = "GENERAL";
        int maxMatches = 0;

        for (var entry : SkillOntology.DOMAIN_SKILLS.entrySet()) {
            int matches = 0;
            for (String skill : entry.getValue()) {
                if (content.contains(skill)) {
                    matches++;
                }
            }

            if (matches > maxMatches) {
                maxMatches = matches;
                bestDomain = entry.getKey();
            }
        }

        return bestDomain;
    }
}