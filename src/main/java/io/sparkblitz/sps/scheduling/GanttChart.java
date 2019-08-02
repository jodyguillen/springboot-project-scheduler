package io.sparkblitz.sps.scheduling;

import io.sparkblitz.sps.dto.ActivityDTO;
import io.sparkblitz.sps.dto.ProjectDTO;

import java.util.Set;
import java.util.TreeSet;

public class GanttChart {
    private ProjectDTO project;
    private final Set<Schedule> schedules = new TreeSet<>();

    public GanttChart() {
    }
    public GanttChart(ProjectDTO project) {
        this.project = project;
    }

    public ProjectDTO getProject() {
        return this.project;
    }

    public void plot(PipeLine pipeLine) {
        this.project = pipeLine.getProject();
        for (ActivityDTO scheduleEntry : pipeLine.getActivities()) {
            int start = scheduleEntry.getStart();
            int finish = scheduleEntry.getFinish();

            for (int timeSlot = start; timeSlot <= finish; timeSlot++) {
                plot(timeSlot, scheduleEntry);
            }
        }
    }

    public void plot(int sequence, ActivityDTO activity) {
        Schedule schedule = schedules.stream()
                .filter(s -> s.getSequence() == sequence)
                .findFirst()
                .orElse(new Schedule(project.getStartDate(), sequence));
        schedule.addActivity(activity);
        schedules.add(schedule);
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }
}
