package io.sparkblitz.sps.controllers;

import io.sparkblitz.sps.models.Activity;
import io.sparkblitz.sps.scheduling.GanttChart;
import io.sparkblitz.sps.services.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/runner")
public class RunnerController {

    @Autowired
    private SchedulingService schedulingService;

    @RequestMapping(value="/activities", method= RequestMethod.POST)
    public List<Activity> generateRandomActivities(@RequestParam("count") Integer count, @RequestParam("duration") Integer duration, @RequestParam("next") Integer successors) {
        return schedulingService.poolRandom(count, duration, successors);
    }

    @RequestMapping(value="/gantt", method= RequestMethod.POST)
    public GanttChart generateGanttChart(@RequestParam("count") Integer count, @RequestParam("duration") Integer duration, @RequestParam("next") Integer successors) {
        return schedulingService.chartRandom(count, duration, successors);
    }
}
