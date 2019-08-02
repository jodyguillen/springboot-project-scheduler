package io.sparkblitz.sps.scheduling;

import io.sparkblitz.sps.dto.ActivityDTO;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Schedule implements Comparable<Schedule> {
    private final int sequence;
    private final Date date;
    private final Set<ActivityDTO> activities;

    public Schedule(Date startDate, int sequence) {
        this.sequence = sequence;
        this.date = Date.from(startDate.toInstant().plus(sequence -1, ChronoUnit.DAYS));
        this.activities = new TreeSet<>();
    }

    public Date getDate() {
        return date;
    }

    public int getSequence() {
        return sequence;
    }

    public boolean addActivity(ActivityDTO e) {
        return activities.add(e);
    }
    public Set<ActivityDTO> getActivities() {
        return activities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return sequence == schedule.sequence;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence);
    }

    @Override
    public int compareTo(Schedule o) {
        return Integer.compare(this.sequence, o.getSequence());
    }


    public String toString(){
        return "[" + this.sequence + "] " + this.date + ": " + this.activities;
    }
}
