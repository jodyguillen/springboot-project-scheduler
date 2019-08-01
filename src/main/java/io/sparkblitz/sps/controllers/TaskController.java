package io.sparkblitz.sps.controllers;

import io.sparkblitz.sps.models.Activity;
import io.sparkblitz.sps.services.SchedulingService;
import io.sparkblitz.sps.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private SchedulingService schedulingService;

    @RequestMapping(value="/projects/{projectId}/tasks", method= RequestMethod.POST)
    public Activity addTask(@PathVariable("projectId") Integer projectId, @RequestBody Activity activity) {
        return activityService.save(projectId, activity);
    }

    @RequestMapping(value="/projects/{projectId}/tasks", method=RequestMethod.GET)
    public List<Activity> getTasksByProjectId(@PathVariable("projectId") Integer projectId) {
        return activityService.findByProjectId(projectId);
    }

    @RequestMapping(value="/projects/{projectId}/tasks/{rootTaskId}/successors/{nextTaskId}", method=RequestMethod.POST)
    public Activity addNext(@PathVariable("rootTaskId") Integer rootTaskId, @PathVariable("nextTaskId") Integer nextTaskId) {
        return activityService.addNextActivity(rootTaskId, nextTaskId);
    }

    @RequestMapping(value="/projects/{projectId}/tasks/{taskId}/successors", method=RequestMethod.GET)
    public List<Activity> getAllNext(@PathVariable("taskId") Integer taskId) {
        return activityService.findSuccessors(taskId);
    }

    @RequestMapping(value="/previous/{id}", method=RequestMethod.GET)
    public boolean hasPrevious(@PathVariable Integer id) {
        return activityService.hasPredecessors(id);
    }

}
