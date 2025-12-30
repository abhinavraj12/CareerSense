package com.careersense.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JobMatchResponse {
    private List<String> skills;
    private List<JobDTO> jobs;
}
