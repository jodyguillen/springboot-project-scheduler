package io.sparkblitz.sps.services;

import io.sparkblitz.sps.dto.ActivityDTO;
import io.sparkblitz.sps.dto.ProjectDTO;
import io.sparkblitz.sps.models.Project;
import io.sparkblitz.sps.models.Activity;
import io.sparkblitz.sps.scheduling.GanttChart;
import io.sparkblitz.sps.scheduling.PipeLine;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SchedulingService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ActivityService activityService;

    public GanttChart chartProjectById(Integer projectId) {
        PipeLine pipeLine = pipeLineProjectById(projectId);
        GanttChart chart = new GanttChart();
        chart.plot(pipeLine);
        return chart;
    }

    public PipeLine pipeLineProjectById(Integer projectId) {
        ProjectDTO project = findByProjectId(projectId);
        if(null == project) {
            return null;
        }

        // Sequence.
        PipeLine pipeLine = new PipeLine(project);
        for(ActivityDTO activity : project.getActivities()) {
            if(!activityService.hasPredecessors(activity.getId())) { // root
                pipeLine.put(0, activity);
            } else {
                traverse(activity, 0, pipeLine);
            }
        }

        return pipeLine;
    }

    public ProjectDTO findByProjectId(Integer projectId) {
        return projectService.findById(projectId)
                .map(project ->new ProjectDTO(project,  (projId) -> activityService.findByProjectId(projId),  (activity) -> activityService.findSuccessors(activity)))
                .orElse(null);
    }

    private void traverse(ActivityDTO activity, int cost, PipeLine pipeLine) {
        if (null == activity) {
            return;
        }

        // Adjust start sequence.
        cost = cost + activity.getDuration(); // total cost (time)
        int start = cost - activity.getDuration() + 1;
        pipeLine.put(start, activity);

        // Traverse successors.
        List<ActivityDTO> next = activity.getNext();
        for (ActivityDTO successor : next) {
            traverse(successor, cost, pipeLine);
        }

    }

    public GanttChart chartByRandomProject(Integer activityCount, Integer maxDuration, Integer maxSuccessorCount) {
        Integer projectId = createAndSaveRandomProject(activityCount, maxDuration, maxSuccessorCount).getId();
        return chartProjectById(projectId);
    }

    public ProjectDTO createAndSaveRandomProject(Integer activityCount, Integer maxDuration, Integer maxSuccessorCount) {
        // Project.
        Project liveProject = new Project();
        liveProject.setName(RandomStringUtils.random(5, true, false).toUpperCase());
        liveProject.setStartDate(new Date());
        ProjectDTO project = new ProjectDTO(projectService.save(liveProject));

        // Activities.
        for(int x = 0; x < activityCount; x++) {
            ActivityDTO activity = createAndSaveRandomActivity(project, maxDuration);
            project.addActivity(activity);
        }

        // Activity Dependencies.
        assignRandomDependencies(project.getActivities(), maxSuccessorCount);

        // All set. Go!
        return project;
    }

    private ActivityDTO createAndSaveRandomActivity(ProjectDTO projectDTO, int maxDuration) {
        Project project = projectService.findById(projectDTO.getId()).orElse(null);

        Activity activity = new Activity();
        activity.setProject(project);
        activity.setName(RandomStringUtils.random(3, true, false).toUpperCase());
        activity.setDuration(RandomUtils.nextInt(1, maxDuration + 1));

        return new ActivityDTO(activityService.save(activity)) ;
    }

    private void assignRandomDependencies(List<ActivityDTO> activities, int maxSuccessorCount) {

        for(int i = 0; i < activities.size() - 1; i++) { // skip last task
            ActivityDTO currentActivityDTO = activities.get(i);
            Activity current = activityService.findById(currentActivityDTO.getId()).orElse(null);
            if(null == current) {
                continue; // Live data longer present. Ignore.
            }

            // Successors.
            int targetNextCount  = RandomUtils.nextInt(0, maxSuccessorCount + 1) ;// 0 - max successor
            int currentNextCount = 0;
            for(int x = i + 1; x < activities.size(); x++) {
                if(targetNextCount == currentNextCount) {
                    break; // Stop adding successors.
                }

                ActivityDTO nextActivityDTO = activities.get(x);
                Activity next = activityService.findById(nextActivityDTO.getId()).orElse(null);
                if(null == next) {
                    continue; // Live data no longer present. Ignore.
                }

                activityService.addNextActivity(current, next); // Write live
                currentActivityDTO.addNext(nextActivityDTO);
                currentNextCount++;
            }
        }
    }
}
