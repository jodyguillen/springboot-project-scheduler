package io.sparkblitz.sps.controllers;

import io.sparkblitz.sps.models.Project;
import io.sparkblitz.sps.scheduling.GanttChart;
import io.sparkblitz.sps.services.ProjectService;
import io.sparkblitz.sps.services.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SchedulingService schedulingService;

    @RequestMapping(value="/projects", method= RequestMethod.POST)
    public Project addProject(@RequestBody Project project) {
        return projectService.save(project);
    }

    @RequestMapping(value="/projects", method=RequestMethod.GET)
    public List<Project> getAllProjects() {
        return projectService.findAll();
    }

    @RequestMapping(value="/projects/{id}/scheduling", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public GanttChart getCalendar(@PathVariable("id") Integer projectId) {
        return schedulingService.chartProjectById(projectId);
    }
}
