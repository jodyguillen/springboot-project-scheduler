package io.sparkblitz.sps.dto;

import io.sparkblitz.sps.models.Activity;
import io.sparkblitz.sps.models.Project;

import java.util.*;
import java.util.stream.Collectors;

public class ProjectDTO implements Comparable<ProjectDTO> {
    private final Integer id;
    private final String name;
    private final Date startDate;
    private List<ActivityDTO> activities = new ArrayList<>();

    @FunctionalInterface
    public interface ActivityGrabber {
        List<Activity> grab(Integer projectId);
    }

    public ProjectDTO(Project project) {
        this(project, null, null);
    }

    public ProjectDTO(Project project, ActivityGrabber activityGrabber, ActivityDTO.NextGrabber nextGrabber) {
        this.id = project.getId();
        this.name = project.getName();
        this.startDate = project.getStartDate();

        if(null != activityGrabber) {
            this.activities = Optional.of(grabActivities(activityGrabber, nextGrabber)).orElse(new ArrayList<>());
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void addActivity(ActivityDTO activity) {
        this.activities.add(activity);
    }

    public List<ActivityDTO> getActivities() {
        return activities;
    }

    @Override
    public int compareTo(ProjectDTO o) {
        return Integer.compare(this.getId(), o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDTO that = (ProjectDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private List<ActivityDTO> grabActivities(ActivityGrabber activityGrabber, ActivityDTO.NextGrabber nextGrabber) {
        if(null != activityGrabber) {
            return activityGrabber.grab(this.getId())
                    .stream()
                    .map(a -> new ActivityDTO(a, nextGrabber))
                    .collect(Collectors.toList());
        }

        return null;
    }

}
