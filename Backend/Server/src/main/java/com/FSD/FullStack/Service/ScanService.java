package com.FSD.FullStack.Service;

import com.FSD.FullStack.Pojos.Scan;
import com.FSD.FullStack.Pojos.ScanRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Service
public class ScanService {

    public Scan performScan(MultipartFile file, ScanRequest scanRequest) throws IOException {
        String fileContent = convertFileToString(file);
        System.out.println("perform scan ");
        

        Map<String, String> issues = new HashMap<>();

        // Perform checks based on scanRequest
        if (scanRequest.isMethodLength()) {
            issues.putAll(checkMethodLength(fileContent));
        }
        if (scanRequest.isCyclomaticComplexity()) {
            issues.putAll(checkCyclomaticComplexity(fileContent));
        }
        if (scanRequest.isCodeDuplication()) {
            issues.putAll(checkCodeDuplication(fileContent));
        }
        if (scanRequest.isExceptionHandling()) {
            issues.putAll(checkExceptionHandling(fileContent));
        }
        if (scanRequest.isNamingConventions()) {
            issues.putAll(checkNamingConventions(fileContent));
        }
        if (scanRequest.isCommenting()) {
            issues.putAll(checkCommenting(fileContent));
            
        }
        if (scanRequest.isResourceManagement()) {
            issues.putAll(checkResourceManagement(fileContent));
        }
        if (scanRequest.isHardcodedValues()) {
            issues.putAll(checkHardcodedValues(fileContent));
        }
        if (scanRequest.isCodeFormatting()) {
            issues.putAll(checkCodeFormatting(fileContent));
        }
        if (scanRequest.isSecurityPractices()) {
            issues.putAll(checkSecurityPractices(fileContent));
        }

        Scan scan = new Scan();
        String description = scanRequest.getDescription();
        scan.setDescription(description);
        scan.setIssues(issues);


        return scan;
    }

    public String convertFileToString(MultipartFile file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        System.out.println("Converting file to string!");
        
        return content.toString();
    }

    public Map<String, String> checkMethodLength(String fileContent) {
        Map<String, String> issues = new HashMap<>();
        System.out.println("fileContent in length fn");
        System.out.println(fileContent);

        String[] lines = fileContent.split("\n");
        int methodLineCount = 0;
        boolean insideMethod = false;
        String methodName = "";

        for (String line : lines) {
            if (line.trim().startsWith("public") || line.trim().startsWith("private") || line.trim().startsWith("protected")) {
                insideMethod = true;
                methodName = line.trim();
                methodLineCount = 1;  
            } else if (insideMethod && line.contains("{")) {
                methodLineCount++;
            } else if (insideMethod && line.contains("}")) {
                methodLineCount++;
                if (methodLineCount > 20) {  
                    issues.put("Method Length", "Method " + methodName + " is too long: " + methodLineCount + " lines");
                }
                else
                {
                	issues.put("Method Length ", "Method Length Rules  are correct keep it up!!");
                }
                insideMethod = false;
                methodLineCount = 0;
                methodName = "";
            } else if (insideMethod) {
                methodLineCount++;
            }
        }

        

        return issues;
    }




    public Map<String, String> checkCyclomaticComplexity(String fileContent) {
        Map<String, String> issues = new HashMap<>();
        String[] lines = fileContent.split("\n");
        int complexity = 0;  // complexity starts at 0 for each new method

        for (String line : lines) {
            if (line.trim().startsWith("public") || line.trim().startsWith("private") || line.trim().startsWith("protected")) {
                if (complexity > 10) {  // Arbitrary threshold for cyclomatic complexity
                    issues.put("Cyclomatic Complexity", "Cyclomatic complexity is too high: " + complexity);
                }
                complexity = 1;  // reset complexity for new method
            }
            if (line.contains("if") || line.contains("else if") || line.contains("for") || line.contains("while") || line.contains("case ")) {
                complexity++;
            }
        }

        // Final check after the loop to catch the last method's complexity
        if (complexity > 10) {
            issues.put("Cyclomatic Complexity", "Cyclomatic complexity is too high: " + complexity);
            
        }
        else
        {
        	issues.put("Cyclomatic Complexity", "no issues with Cyclomatic Complexity keep it up!!");
        }

        return issues;
    }

    public Map<String, String> checkCodeDuplication(String fileContent) {
        Map<String, String> issues = new HashMap<>();
        String[] lines = fileContent.split("\n");
        Map<String, Integer> lineOccurrences = new HashMap<>();

        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty()) {
                lineOccurrences.put(line, lineOccurrences.getOrDefault(line, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : lineOccurrences.entrySet()) {
            if (entry.getValue() > 1) {
                issues.put("Code Duplication", "Duplicate code found: \"" + entry.getKey() + "\" appears " + entry.getValue() + " times");
            }
        }

        return issues;
    }

    public Map<String, String> checkExceptionHandling(String fileContent) {
        Map<String, String> issues = new HashMap<>();
        if (!fileContent.contains("try {")) {
            issues.put("Exception Handling", "No try-catch blocks found in the code.");
        }
        return issues;
    }

    public Map<String, String> checkNamingConventions(String fileContent) {
        Map<String, String> issues = new HashMap<>();
        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            if (line.contains("class ")) {
                String className = line.split("class ")[1].split(" ")[0];
                if (!className.isEmpty() && !Character.isUpperCase(className.charAt(0))) {
                    issues.put("Naming Conventions", "Class name \"" + className + "\" should start with an uppercase letter.");
                }
            }
            if (line.contains(" void ") || line.contains(" int ") || line.contains(" String ") || line.contains(" boolean ")) {
                String[] parts = line.split(" ");
                String methodName = parts.length > 2 ? parts[2].split("\\(")[0] : "";
                if (!methodName.isEmpty() && !Character.isLowerCase(methodName.charAt(0))) {
                    issues.put("Naming Conventions", "Method name \"" + methodName + "\" should start with a lowercase letter.");
                }
            }
        }

        return issues;
    }

    public Map<String, String> checkCommenting(String fileContent) {
        Map<String, String> issues = new HashMap<>();
        String[] lines = fileContent.split("\n");
        int commentCount = 0;

        for (String line : lines) {
            if (line.trim().startsWith("//") || line.trim().startsWith("/*") || line.trim().startsWith("*")) {
                commentCount++;
            }
        }

        if (commentCount == 0) {
            issues.put("Commenting", "No comments found in the code.");
        }

        return issues;
    }


    public Map<String, String> checkResourceManagement(String fileContent) {
        Map<String, String> issues = new HashMap<>();
        String[] lines = fileContent.split("\n");
        boolean foundResourceManagement = false;

        for (String line : lines) {
            if (line.contains("try (") || line.contains("finally {")) {
                foundResourceManagement = true;
                break;
            }
        }

        if (!foundResourceManagement) {
            issues.put("Resource Management", "No proper resource management found. Consider using try-with-resources or finally blocks.");
        }

        return issues;
    }


    public Map<String, String> checkHardcodedValues(String fileContent) {
        Map<String, String> issues = new HashMap<>();
        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            if (line.matches(".*\\d+.*") && !line.trim().startsWith("//")) {  // Check for numbers not in comments
                issues.put("Hardcoded Values", "Hardcoded value found: " + line.trim());
            }
            if (line.matches(".*\".*\".*") && !line.trim().startsWith("//")) {  // Check for strings not in comments
                issues.put("Hardcoded Values", "Hardcoded string found: " + line.trim());
            }
        }

        return issues;
    }


    public Map<String, String> checkCodeFormatting(String fileContent) {
        Map<String, String> issues = new HashMap<>();
        String[] lines = fileContent.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (!line.matches("\\s*\\{?\\s*\\}?\\s*") && (line.startsWith(" ") || line.startsWith("\t"))) {
                issues.put("Code Formatting", "Possible indentation issue at line " + (i + 1) + ": " + line.trim());
            }
        }

        return issues;
    }


    public Map<String, String> checkSecurityPractices(String fileContent) {
        Map<String, String> issues = new HashMap<>();
        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            if (line.contains("password") || line.contains("secret") || line.contains("apikey")) {
                issues.put("Security Practices", "Possible hardcoded credential found: " + line.trim());
            }
        }

        return issues;
    }


    public Scan getScanResult(Long id) {
        // Placeholder: Implement logic to retrieve scan result by ID
        return new Scan();
    }
    public String formatIssues(Map<String, String> issues) {
        StringJoiner formattedIssues = new StringJoiner("\n");
        int issueNumber = 1;

        for (Map.Entry<String, String> entry : issues.entrySet()) {
            formattedIssues.add(issueNumber + ". " + entry.getKey() + ": " + entry.getValue());
            issueNumber++;
        }

        return formattedIssues.toString();
    }
    
}
