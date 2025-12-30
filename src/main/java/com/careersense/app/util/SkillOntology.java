package com.careersense.app.util;

import java.util.*;

public class SkillOntology {

    public static final Map<String, List<String>> DOMAIN_SKILLS = Map.of(
            "SOFTWARE_ENGINEERING", List.of(
                    "java", "spring", "api", "sql", "microservices",
                    "react", "docker", "git", "aws", "system design",
                    "kubernetes", "cloud-native", "graphql", "typescript",
                    "angular", "node.js", "ci/cd", "unit testing", "design patterns"
            ),

            "DATA_ANALYTICS", List.of(
                    "python", "excel", "sql", "power bi", "tableau",
                    "statistics", "data analysis", "machine learning",
                    "deep learning", "pandas", "numpy", "scikit-learn",
                    "tensorflow", "spark", "big data", "data visualization"
            ),

            "PRODUCT_MANAGEMENT", List.of(
                    "roadmap", "stakeholder management", "agile", "scrum",
                    "user stories", "market research", "wireframing",
                    "product lifecycle", "competitive analysis"
            ),

            "FINANCE", List.of(
                    "financial modeling", "budgeting", "forecasting",
                    "excel", "erp", "sap", "tax compliance",
                    "investment analysis", "risk management"
            ),

            "MARKETING", List.of(
                    "seo", "content marketing", "google ads",
                    "social media", "email campaigns", "analytics",
                    "content strategy", "brand management",
                    "marketing automation", "hubspot", "customer segmentation"
            ),

            "HR", List.of(
                    "recruitment", "onboarding", "payroll",
                    "employee engagement", "hr policies",
                    "performance management", "talent development",
                    "employee relations", "compliance", "diversity & inclusion"
            ),

            "GENERAL", List.of(
                    "communication", "leadership", "problem solving",
                    "time management", "teamwork", "critical thinking",
                    "adaptability", "emotional intelligence"
            )
    );

    public static final Map<String, List<String>> ROLE_MAPPING = Map.of(
            "SOFTWARE_ENGINEERING", List.of(
                    "Backend Developer",
                    "Full Stack Developer",
                    "Software Engineer",
                    "Java Developer",
                    "Cloud Engineer"
            ),
            "DATA_ANALYTICS", List.of(
                    "Data Analyst",
                    "Business Analyst",
                    "Data Scientist"
            ),
            "PRODUCT_MANAGEMENT", List.of(
                    "Product Manager",
                    "Associate Product Manager"
            ),
            "FINANCE", List.of(
                    "Financial Analyst",
                    "Accountant",
                    "Investment Analyst"
            ),
            "MARKETING", List.of(
                    "Digital Marketer",
                    "Marketing Executive",
                    "Brand Strategist"
            ),
            "HR", List.of(
                    "HR Executive",
                    "Talent Acquisition Specialist",
                    "HR Business Partner"
            )
    );
}