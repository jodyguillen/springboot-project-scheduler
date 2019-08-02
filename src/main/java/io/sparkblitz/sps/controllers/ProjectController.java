package io.sparkblitz.sps.controllers;

import io.sparkblitz.sps.models.Project;
import io.sparkblitz.sps.scheduling.GanttChart;
import io.sparkblitz.sps.services.ProjectService;
import io.sparkblitz.sps.services.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SchedulingService schedulingService;

    @RequestMapping(value="/projects/open", method= RequestMethod.GET)
    public String openForm(Project project) {
        return "create-project";
    }

    @RequestMapping(value="/projects", method= RequestMethod.POST)
    public String addProject(Project project, Model model) {
        Project saved = projectService.save(project);
        model.addAttribute("project", saved);
        return "redirect:/projects";
    }

    @RequestMapping(value="/projects", method=RequestMethod.GET)
    @ResponseBody
    public List<Project> getAllProjects() {
        return projectService.findAll();
    }

    @RequestMapping(value="/projects/{id}/scheduling", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public GanttChart getCalendar(@PathVariable("id") Integer projectId) {
        return schedulingService.chartProjectById(projectId);
    }
}
