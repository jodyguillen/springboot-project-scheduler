package io.sparkblitz.sps.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sparkblitz.sps.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import io.sparkblitz.sps.services.ProjectService;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class UploadController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping("/")
    public String viewUploadForm() {
        return "upload";
    }

    @RequestMapping(value="/projects/upload", method = RequestMethod.POST)
    public String uploadProject(@RequestPart("file")MultipartFile file, Model model) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = file.getInputStream();
        projectService.removeAll();
        Project project =  projectService.save(mapper.readValue(inputStream, Project.class));
        System.out.println("Project saved!");
        model.addAttribute("project", project);
        return "project";
    }
}
