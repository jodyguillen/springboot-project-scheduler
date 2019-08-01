package io.sparkblitz.sps.services;

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


    public GanttChart chartByProjectId(Integer projectId) {
        PipeLine pipeLine = pipeLineActivities(projectId);
        GanttChart chart = new GanttChart();
        chart.plot(pipeLine);
        return chart;
    }

    public PipeLine pipeLineActivities(Integer projectId) {
        List<Activity> activities = activityService.findByProjectId(projectId);
        if(null == activities || activities.isEmpty()) {
            return null;
        }

        // Sequence
        Project project = activities.stream().findFirst().map(a -> a.getProject()).orElseThrow(() -> new IllegalStateException("Orphan activity."));
        PipeLine pipeLine = new PipeLine(project);
        for(Activity activity : activities) {
            if(!activityService.hasPredecessors(activity)) { // root
                pipeLine.put(0, activity);
            } else {
                traverse(activity, 0, pipeLine);
            }
        }

        return pipeLine;
    }

    public GanttChart chartRandom(Integer count, Integer maxDuration, Integer maxSuccessorCount) {
        Integer projectId = poolRandom(count, maxDuration, maxSuccessorCount)
                .stream()
                .findFirst()
                .map(a -> a.getProject().getId())
                .orElse(null);

        return chartByProjectId(projectId);
    }

    public List<Activity> poolRandom(Integer count, Integer maxDuration, Integer maxSuccessorCount) {
        Project project = createRandomProject();
        List<Activity> activities = poolActivities(project.getId(), count, maxDuration);
        assignDependencies(activities, maxSuccessorCount);
        return activities;
    }

    public Project createRandomProject() {
        Project project = new Project();
        project.setName(RandomStringUtils.random(5, true, false).toUpperCase());
        project.setStartDate(new Date());
        return projectService.save(project);
    }

    public List<Activity> poolActivities(Integer projectId, int count, int maxDuration) {
        List activities = new ArrayList<Activity>();
        for(int i=1; i<=count; i++) {
            Activity random = createRandomActivity(projectId, maxDuration);
            activities.add(activityService.save(random));
        }

        return activities;
    }


    public Activity createRandomActivity(Integer projectId, int maxDuration) {
        Project project = projectService.findById(projectId).orElse(null);
        Activity activity = new Activity();
        activity.setProject(project);
        activity.setName(RandomStringUtils.random(3, true, false).toUpperCase());
        activity.setDuration(RandomUtils.nextInt(1, maxDuration + 1));
        return activity;
    }

    public void assignDependencies(List<Activity> activities, int maxSuccessorCount) {

        for(int i = 0; i < activities.size() - 1; i++) { // skip last task
            int targetNextCount = RandomUtils.nextInt(0, maxSuccessorCount + 1) ;// 0 - max successor
            Activity current = activities.get(i);

            int currentNextCount = 0;
            for(int x = i + 1; x < activities.size(); x++) {
                if(targetNextCount == currentNextCount) {
                    Activity next = activities.get(x);
                    activityService.addNextActivity(current, next);
                }

                currentNextCount++;
            }
        }
    }

    private void traverse(Activity activity, int cost, PipeLine pipeLine) {
        if (null == activity) {
            return;
        }

        cost = cost + activity.getDuration(); // total cost (time)
        int start = cost - activity.getDuration() + 1;
        pipeLine.put(start, activity);
        List<Activity> next = activityService.findSuccessors(activity);
        for (Activity n : next) {
            traverse(n, cost, pipeLine);
        }

    }
}
