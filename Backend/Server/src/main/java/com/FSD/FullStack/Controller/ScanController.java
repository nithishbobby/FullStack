package com.FSD.FullStack.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.FSD.FullStack.Pojos.Scan;
import com.FSD.FullStack.Pojos.ScanRequest;
import com.FSD.FullStack.Service.ScanService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/scan")
@CrossOrigin(origins = "http://localhost:3000") 
public class ScanController {

    @Autowired
    private ScanService scanService;

    @PostMapping
    public ResponseEntity<Scan> performScan(@RequestParam("file") MultipartFile file,
                                            @RequestParam("description") String description,
                                            @RequestParam("selectedChecksJson") String selectedChecksJson) {
        if (file.isEmpty()) {
            System.out.println("File is empty");
            return ResponseEntity.badRequest().body(null);
        }

        System.out.println("Description: " + description);
        System.out.println("Selected Checks: " + selectedChecksJson);
        System.out.println("file: " + file.getOriginalFilename());  
        ScanRequest scanRequest;
        System.out.println("Before JSON parsing");

        try {
            scanRequest = new ObjectMapper().readValue(selectedChecksJson, ScanRequest.class);
            System.out.println(scanRequest);
        } catch (IOException e) {
            System.err.println("JSON parsing error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }

        scanRequest.setDescription(description);
        System.out.println("After setting description");

        try {
            System.out.println("Before calling scanService.performScan");
            Scan scan = scanService.performScan(file, scanRequest);
            System.out.println("After calling scanService.performScan");
            System.out.println(scan);
            return ResponseEntity.ok(scan);
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        } catch (Exception e) {
            System.err.println("Unexpected Exception: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scan> getScanResult(@PathVariable Long id) {
        try {
            Scan scan = scanService.getScanResult(id);
            return ResponseEntity.ok(scan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
