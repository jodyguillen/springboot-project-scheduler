package io.sparkblitz.sps.controllers;

import io.sparkblitz.sps.models.Task;
import io.sparkblitz.sps.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value="/projects/{projectId}/tasks", method= RequestMethod.POST)
    public Task addTask(@PathVariable("projectId") Integer projectId, @RequestBody Task task) {
        return taskService.save(projectId, task);
    }

    @RequestMapping(value="/projects/{projectId}/tasks", method=RequestMethod.GET)
    public List<Task> getTasksByProjectId(@PathVariable("projectId") Integer projectId) {
        return taskService.findByProjectId(projectId);
    }
}
