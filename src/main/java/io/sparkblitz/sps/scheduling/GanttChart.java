package io.sparkblitz.sps.scheduling;

import io.sparkblitz.sps.models.Activity;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GanttChart {
    private Date startDate;
    private final Set<Schedule> schedules;

    public GanttChart() {
        this(null);
    }

    public GanttChart(Date startDate) {
        this.startDate = startDate;
        this.schedules = new TreeSet<>();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void plot(PipeLine pipeLine) {
        this.startDate = pipeLine.getProject().getStartDate();
        for (Schedule.Entry scheduleEntry : pipeLine.getEntries()) {
            int start = scheduleEntry.getStart();
            int finish = scheduleEntry.getEnd();

            for (int timeSlot = start; timeSlot <= finish; timeSlot++) {
                addSchedule(timeSlot, scheduleEntry);
            }
        }
    }

    public void addSchedule(int sequence, Schedule.Entry entry) {
        Schedule schedule = schedules.stream().filter(s ->s.getSequence() == sequence).findFirst().orElse(new Schedule(startDate, sequence));
        schedule.addTask(entry);
        schedules.add(schedule);
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }
}
