package io.sparkblitz.sps.controllers;

import io.sparkblitz.sps.dto.ProjectDTO;
import io.sparkblitz.sps.models.Activity;
import io.sparkblitz.sps.models.Project;
import io.sparkblitz.sps.scheduling.GanttChart;
import io.sparkblitz.sps.services.ActivityService;
import io.sparkblitz.sps.services.ProjectService;
import io.sparkblitz.sps.services.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/runner")
public class RandomRunnerController {

    @Autowired
    private SchedulingService schedulingService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping(value="/projects", method= RequestMethod.POST)
    public String getProject(@RequestParam("count") Integer count, @RequestParam("duration") Integer duration, @RequestParam("next") Integer successors, Model model) {
        ProjectDTO project =schedulingService.createAndSaveRandomProject(count, duration, successors);
        model.addAttribute("project", project);
        return "activity-list";
    }

    @RequestMapping(value="/schedules", method= RequestMethod.POST)
    public String getChart(@RequestParam("count") Integer count, @RequestParam("duration") Integer duration, @RequestParam("next") Integer successors, Model model) {
        GanttChart ganttChart =  schedulingService.chartByRandomProject(count, duration, successors);
        model.addAttribute("chart", ganttChart);
        return "schedule-chart";
    }

    @RequestMapping(value="/step0", method=RequestMethod.GET)
    public String start(Project project, Model model) {
        model.addAttribute("activities", null);
        return "index";
    }

    @RequestMapping(value="/step1", method=RequestMethod.POST)
    public String createProject(Project project, Activity activity, Model model) {
        model.addAttribute("project", projectService.save(project));
        model.addAttribute("activity", activity);
        model.addAttribute("activities", null);
        return "manage-activities";
    }

    @RequestMapping(value="/step2/{id}", method=RequestMethod.GET)
    public String viewProject(@PathVariable("id") Integer projectId, Model model) {
        Project project = projectService.findById(projectId).orElse(null);
        model.addAttribute("project", project);
        model.addAttribute("activities", activityService.findByProjectId(project.getId()));
        return "manage-activities";
    }

    @RequestMapping(value="/step3/{id}/activities", method=RequestMethod.POST)
    public String createActivity(@PathVariable("id") Integer projectId, Activity activity, Model model) {
        activity = activityService.save(projectId, activity);
        model.addAttribute("project", activity.getProject());
        model.addAttribute("activities", activityService.findByProjectId(projectId));
        return "manage-activities";
    }
}
