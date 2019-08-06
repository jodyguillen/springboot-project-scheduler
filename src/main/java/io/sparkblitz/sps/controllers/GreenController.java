package io.sparkblitz.sps.controllers;

import io.sparkblitz.sps.dto.ActivityDTO;
import io.sparkblitz.sps.dto.ProjectDTO;
import io.sparkblitz.sps.models.Activity;
import io.sparkblitz.sps.models.ActivityDependencyId;
import io.sparkblitz.sps.models.Project;
import io.sparkblitz.sps.services.ActivityService;
import io.sparkblitz.sps.services.ProjectService;
import io.sparkblitz.sps.services.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/green")
public class GreenController {

    @Autowired
    private SchedulingService schedulingService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value="/projects", method = RequestMethod.POST)
    public String createProject (@RequestBody Project project, Model model) {
      Project savedProject = projectService.save(project);
      return String.format("redirect:/green/projects/%s/activities", savedProject.getId());
    }

    @RequestMapping(value="/projects/{id}/activities", method = RequestMethod.GET)
    public String viewProject(@PathVariable("id") Integer projectId, Activity activityPlaceHolder, Model model) {
        Project project = projectService.findById(projectId).orElse(new Project());
        List<Activity> activities = activityService.findByProject(project);
        model.addAttribute("project", project);
        model.addAttribute("activities", activities);
        return "green";
    }

    @RequestMapping(value="/projects/{id}/activities", method = RequestMethod.POST)
    public String addActivity(@PathVariable("id") Integer projectId, Activity activity) {
        activityService.save(projectId, activity);
        return String.format("redirect:/green/projects/%s/activities", projectId);
    }

    @RequestMapping(value="/projects/{id}/schedules", method = RequestMethod.GET)
    public String viewSchedules(@PathVariable("id") Integer projectId, ActivityDependencyId dependency, Model model) {
        Project project = projectService.findById(projectId).orElse(new Project());
        List<Activity> activities = activityService.findByProject(project);
        model.addAttribute("project", project);
        model.addAttribute("dependency", dependency);
        model.addAttribute("activities", activities);
        for(Activity a : activities) {
            a.setNext(activityService.findSuccessors(a));
        }
        return "schedules";
    }

    @RequestMapping(value="/projects/{id}/schedules", method = RequestMethod.POST)
    public String addNext(@PathVariable("id") Integer projectId, ActivityDependencyId dependency) {
        System.out.println(String.format("Received | root=%s | next=%s",  dependency.getRootTaskId(), dependency.getNextTaskId()));
        activityService.addNextActivity(dependency.getRootTaskId(), dependency.getNextTaskId());
        return String.format("redirect:/green/projects/%s/schedules", projectId);
    }
}
