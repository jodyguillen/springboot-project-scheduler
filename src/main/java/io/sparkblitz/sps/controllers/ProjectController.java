package io.sparkblitz.sps.controllers;

import io.sparkblitz.sps.models.Project;
import io.sparkblitz.sps.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value="/projects", method= RequestMethod.POST)
    public Project addProject(@RequestBody Project project) {
        return projectService.save(project);
    }

    @RequestMapping(value="/projects", method=RequestMethod.GET)
    public List<Project> getAllProjects() {
        return projectService.findAll();
    }
}
