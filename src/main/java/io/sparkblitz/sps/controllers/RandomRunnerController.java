package io.sparkblitz.sps.controllers;

import io.sparkblitz.sps.dto.ProjectDTO;
import io.sparkblitz.sps.scheduling.GanttChart;
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
}
