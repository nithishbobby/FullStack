package com.FSD.FullStack.Pojos;

import java.util.Map;

public class Scan {

    private Long id;
    private String description;
    private Map<String, String> issues;

    public Scan() {
    }

    public Scan(Long id, String description, Map<String, String> issues) {
        this.id = id;
        this.description = description;
        this.issues = issues;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getIssues() {
        return issues;
    }

    public void setIssues(Map<String, String> issues) {
        this.issues = issues;
    }
}
