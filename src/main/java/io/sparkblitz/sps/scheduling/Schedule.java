package io.sparkblitz.sps.scheduling;

import io.sparkblitz.sps.models.Activity;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Schedule implements Comparable<Schedule> {
    private final int sequence;
    private final Date date;
    private final Set<Entry> task;

    public Schedule(Date startDate, int sequence) {
        this.sequence = sequence;
        this.date = Date.from(startDate.toInstant().plus(sequence -1, ChronoUnit.DAYS));
        this.task = new TreeSet<>();
    }

    public Date getDate() {
        return date;
    }

    public int getSequence() {
        return sequence;
    }

    public boolean addTask(Entry e) {
        return task.add(e);
    }
    public Set<Entry> getTask() {
        return task;
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
        return "[" + this.sequence + "] " + this.date + ": " + this.task;
    }



    public static Entry createEntry(Activity activity) {
        return new Entry(activity);
    }

    public static class Entry implements Comparable<Entry> {
        private final Integer id;
        private final String name;
        private final int duration;
        private int start;
        private int end;

        private Entry(Activity activity) {
            this.id = activity.getId();
            this.name = activity.getName();
            this.duration = activity.getDuration();
            this.start = 1;
            this.end = computeEnd();
        }

        @Override
        public int compareTo(Entry o) {
            return Integer.compare(this.id, o.getId());
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getDuration() {
            return duration;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            if(start > this.start) {
                this.start = start;
                this.end = computeEnd();
            }
        }

        public int getEnd() {
            return end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return Objects.equals(id, entry.getId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Schedule.Entry{" +
                    "name='" + name + '\'' +
                    ", duration=" + duration +
                    ", start=" + start +
                    ", end=" + end +
                    '}';
        }

        private int computeEnd(){
            return this.start + this.duration - 1;
        }
    }
}
