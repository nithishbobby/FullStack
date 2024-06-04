package com.FSD.FullStack.Pojos;

public class ScanRequest {
    private boolean methodLength;
    private boolean cyclomaticComplexity;
    private boolean codeDuplication;
    private boolean exceptionHandling;
    private boolean namingConventions;
    private boolean commenting;
    private boolean resourceManagement;
    private boolean hardcodedValues;
    private boolean codeFormatting;
    private boolean securityPractices;
    private String description;

    // Getters and Setters

    public boolean isMethodLength() {
        return methodLength;
    }

    public void setMethodLength(boolean methodLength) {
        this.methodLength = methodLength;
    }

    public boolean isCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity(boolean cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    public boolean isCodeDuplication() {
        return codeDuplication;
    }

    public void setCodeDuplication(boolean codeDuplication) {
        this.codeDuplication = codeDuplication;
    }

    public boolean isExceptionHandling() {
        return exceptionHandling;
    }

    public void setExceptionHandling(boolean exceptionHandling) {
        this.exceptionHandling = exceptionHandling;
    }

    public boolean isNamingConventions() {
        return namingConventions;
    }

    public void setNamingConventions(boolean namingConventions) {
        this.namingConventions = namingConventions;
    }

    public boolean isCommenting() {
        return commenting;
    }

    public void setCommenting(boolean commenting) {
        this.commenting = commenting;
    }

    public boolean isResourceManagement() {
        return resourceManagement;
    }

    public void setResourceManagement(boolean resourceManagement) {
        this.resourceManagement = resourceManagement;
    }

    public boolean isHardcodedValues() {
        return hardcodedValues;
    }

    public void setHardcodedValues(boolean hardcodedValues) {
        this.hardcodedValues = hardcodedValues;
    }

    public boolean isCodeFormatting() {
        return codeFormatting;
    }

    public void setCodeFormatting(boolean codeFormatting) {
        this.codeFormatting = codeFormatting;
    }

    public boolean isSecurityPractices() {
        return securityPractices;
    }

    public void setSecurityPractices(boolean securityPractices) {
        this.securityPractices = securityPractices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ScanRequest{" +
                "methodLength=" + methodLength +
                ", cyclomaticComplexity=" + cyclomaticComplexity +
                ", codeDuplication=" + codeDuplication +
                ", exceptionHandling=" + exceptionHandling +
                ", namingConventions=" + namingConventions +
                ", commenting=" + commenting +
                ", resourceManagement=" + resourceManagement +
                ", hardcodedValues=" + hardcodedValues +
                ", codeFormatting=" + codeFormatting +
                ", securityPractices=" + securityPractices +
                ", description='" + description + '\'' +
                '}';
    }
}
