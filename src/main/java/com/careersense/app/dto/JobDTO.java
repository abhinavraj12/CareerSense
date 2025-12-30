package com.careersense.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobDTO {
    private String title;
    private String company;
    private String location;
    private String applyLink;
    private String source;
}