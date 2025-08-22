package com.portfolio.builder.controller;

import com.portfolio.builder.model.UserPortfolio;
import com.portfolio.builder.repository.PortfolioRepository;
import com.portfolio.builder.service.TemplateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioRepository repo;
    private final TemplateService templateService;

    public PortfolioController(PortfolioRepository repo, TemplateService templateService) {
        this.repo = repo;
        this.templateService = templateService;
    }

    @Value("${app.cors.allowedOrigin:http://localhost:4200}")
    private String allowedOrigin;

    @CrossOrigin(origins = "${app.cors.allowedOrigin:http://localhost:4200}")
    @PostMapping("/create")
    public ResponseEntity<UserPortfolio> create(@Valid @RequestBody UserPortfolio dto) {
        return ResponseEntity.ok(repo.save(dto));
    }

    @CrossOrigin(origins = "${app.cors.allowedOrigin:http://localhost:4200}")
    @GetMapping("/{id}")
    public ResponseEntity<UserPortfolio> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "${app.cors.allowedOrigin:http://localhost:4200}")
    @PostMapping(value = "/preview", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> preview(@RequestBody UserPortfolio dto) throws IOException {
        String html = templateService.loadTemplate(dto.getTheme(), getClass().getClassLoader());
        String rendered = templateService.replacePlaceholders(html, toMap(dto));
        return ResponseEntity.ok(rendered);
    }

    @CrossOrigin(origins = "${app.cors.allowedOrigin:http://localhost:4200}")
    @PostMapping(value = "/generate-zip")
    public ResponseEntity<byte[]> zip(@RequestBody UserPortfolio dto) throws IOException {
        String html = templateService.loadTemplate(dto.getTheme(), getClass().getClassLoader());
        String rendered = templateService.replacePlaceholders(html, toMap(dto));
        byte[] zipBytes = templateService.buildZip(dto.getTheme(), rendered, getClass().getClassLoader());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=portfolio-" + dto.getFullName().replaceAll("\s+","-") + ".zip");
        return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
    }

    private Map<String,String> toMap(UserPortfolio p) {
        Map<String,String> m = new HashMap<>();
        m.put("fullName", nullToEmpty(p.getFullName()));
        m.put("email", nullToEmpty(p.getEmail()));
        m.put("summary", nullToEmpty(p.getSummary()));
        m.put("skills", nullToEmpty(p.getSkills()));
        m.put("projects", nullToEmpty(p.getProjects()));
        m.put("socialLinks", nullToEmpty(p.getSocialLinks()));
        return m;
    }

    private String nullToEmpty(String s) { return s == null ? "" : s; }
}
