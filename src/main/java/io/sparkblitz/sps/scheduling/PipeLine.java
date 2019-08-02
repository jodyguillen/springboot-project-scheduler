package io.sparkblitz.sps.scheduling;

import io.sparkblitz.sps.dto.ActivityDTO;
import io.sparkblitz.sps.dto.ProjectDTO;

import java.util.Set;
import java.util.TreeSet;

public class PipeLine {

    private final ProjectDTO project;
   private Set<ActivityDTO> activities;

    public PipeLine(ProjectDTO project){
        this.project = project;
        this.activities = new TreeSet<>();
    }

    public ProjectDTO getProject() {
        return project;
    }

    public Set<ActivityDTO> getActivities() {
        return activities;
    }

    public void put(int start, ActivityDTO activity) {
        this.activities.add(activity);
        ActivityDTO todo = this.activities.stream().filter(a -> a.equals(activity)).findFirst().orElse(null);
        todo.setStart(start);
    }

    private ActivityDTO getActivity(ActivityDTO activity) {
        this.activities.add(activity);
        return this.activities.stream().filter(a -> a.equals(activity)).findFirst().orElse(null);
    }
}
