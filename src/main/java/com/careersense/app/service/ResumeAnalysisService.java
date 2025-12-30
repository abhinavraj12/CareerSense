package com.careersense.app.service;

import com.careersense.app.dto.ResumeAnalysisResult;
import com.careersense.app.util.ResumeScoringUtil;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ResumeAnalysisService {

    private final OpenAIResumeService openAIResumeService;
    private final Tika tika = new Tika();

    public ResumeAnalysisResult analyzeResume(
            MultipartFile file,
            int yearsOfExperience
    ) throws Exception {

        String resumeText = tika.parseToString(file.getInputStream());

        if (resumeText.length() > 3000) {
            resumeText = resumeText.substring(0, 3000);
        }

        ResumeScoringUtil.Result baseResult =
                ResumeScoringUtil.analyze(
                        resumeText,
                        file.getOriginalFilename(),
                        yearsOfExperience
                );

        String aiSuggestions;
        try {
            aiSuggestions = openAIResumeService.generateSuggestions(
                    resumeText,
                    yearsOfExperience
            );
        } catch (Exception ex) {
            aiSuggestions = "AI suggestions temporarily unavailable.";
        }

        return new ResumeAnalysisResult(
                baseResult.score(),
                baseResult.atsFriendly(),
                baseResult.experienceLevel(),
                baseResult.matchedSkills(),
                baseResult.missingSkills(),
                baseResult.suggestedRoles(),
                baseResult.suggestions(),
                aiSuggestions
        );
    }
}
