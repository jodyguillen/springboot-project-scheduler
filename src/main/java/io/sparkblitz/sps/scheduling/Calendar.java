package io.sparkblitz.sps.scheduling;

import io.sparkblitz.sps.domain.Activity;
import io.sparkblitz.sps.domain.Project;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

public class Calendar {
    private final Project project;
    private final Date startDate;
    private final TimeUnit timeUnit;
    private final Set<Schedule> schedules = new TreeSet<>();

    public Calendar(Project project, Date startDate, String timeUnitName) {
        this.project = project;
        this.startDate = startDate == null ? project.getStartDate() : startDate;
        TimeUnit candidateUnit = Optional.ofNullable(timeUnitName)
                .map(name -> TimeUnit.from(timeUnitName.toUpperCase()))
                .orElse(project.getTimeUnit() == null ? TimeUnit.DAY : TimeUnit.from(project.getTimeUnit().toUpperCase()));
        this.timeUnit = candidateUnit == null ? TimeUnit.DAY : candidateUnit; // Default unit = DAY
    }

    public Calendar(Project project, Date startDate) {
        this(project, startDate, project.getTimeUnit());
    }

    public void schedule(Function<String, List<Activity>> dependencyFetcher) {
        Iterable<Activity> activities = project.getActivities();

        // Adjust sequence.
        for(Activity activity : activities) {
            traverse(activity, 0, dependencyFetcher);
        }

        // Plot schedule.
        for(Activity activity : activities) {
            int start = activity.getStart();
            int finish = activity.getFinish();

            for (int ordinal = start; ordinal <= finish; ordinal++) {
                plot(ordinal, activity);
            }
        }
    }

    public Project getProject() {
        return project;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    private void plot(int ordinal, Activity activity) {
        Schedule schedule = schedules.stream().filter(s -> s.getOrdinal() == ordinal).findFirst().orElse(new Schedule(ordinal, this.startDate, this.timeUnit));
        schedule.put(activity);
        this.schedules.add(schedule);
    }

    private void traverse(Activity activity, int cost, Function<String, List<Activity>> dependencyFetcher) {
        if (null == activity) {
            return;
        }

        // Adjust start order.
        cost = cost + activity.getDuration(); // total cost (time)
        int start = cost - activity.getDuration() + 1;
        activity.setStart(start);

        // Traverse successors.
        List<Activity> next = null == dependencyFetcher ? new ArrayList<>() : dependencyFetcher.apply(activity.getCode());
        for (Activity successor : next) {
            traverse(successor, cost, dependencyFetcher);
        }

    }
    public static final class Schedule implements Comparable<Schedule> {
        private final int ordinal;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private final Date date;
        private final Set<Activity> activities = new LinkedHashSet<>();

        private Schedule(int ordinal, Date calendarStartDate, TimeUnit timeUnit) {
            this.ordinal = ordinal;
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(calendarStartDate);
            calendar.add(timeUnit.getCalendarUnit(), ordinal -1);
            this.date = calendar.getTime();
        }

        private void put(Activity activity) {
            this.activities.add(activity);
        }

        public int getOrdinal() {
            return this.ordinal;
        }

        public Set<Activity> getActivities(){
            return this.activities;
        }

        public Date getDate() {
            return date;
        }

        @Override
        public int compareTo(Schedule o) {
            return Integer.compare(this.ordinal, o.getOrdinal());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Schedule schedule = (Schedule) o;
            return ordinal == schedule.ordinal;
        }

        @Override
        public int hashCode() {
            return Objects.hash(ordinal);
        }
    }
}
