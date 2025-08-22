package com.portfolio.builder.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class TemplateService {

    public String loadTemplate(String theme, ClassLoader cl) throws IOException {
        String path = "templates/themes/" + theme + "/index.html";
        try (InputStream is = cl.getResourceAsStream(path)) {
            if (is == null) throw new FileNotFoundException("Template not found: " + path);
            return new String(is.readAllBytes());
        }
    }

    public String replacePlaceholders(String html, Map<String, String> vars) {
        String out = html;
        for (Map.Entry<String, String> e : vars.entrySet()) {
            out = out.replace("{{" + e.getKey() + "}}", e.getValue() == null ? "" : e.getValue());
        }
        return out;
    }

    public byte[] buildZip(String theme, String renderedIndex, ClassLoader cl) throws IOException {
        // Copy the theme folder (assets etc.), but replace index.html with renderedIndex
        String base = "templates/themes/" + theme + "/";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            // Walk resources: since resources in JAR/classpath can't be walked easily,
            // we include only expected assets folders.
            String[] assetDirs = new String[] {"assets/css"};
            for (String dir : assetDirs) {
                String assetPath = base + dir + "/";
                // Try known files
                String[] files = new String[] {"style.css"};
                for (String file : files) {
                    String cp = assetPath + file;
                    try (InputStream is = cl.getResourceAsStream(cp)) {
                        if (is != null) {
                            zos.putNextEntry(new ZipEntry(dir + "/" + file));
                            is.transferTo(zos);
                            zos.closeEntry();
                        }
                    }
                }
            }
            // Add index.html
            zos.putNextEntry(new ZipEntry("index.html"));
            zos.write(renderedIndex.getBytes());
            zos.closeEntry();
        }
        return baos.toByteArray();
    }
}
