package io.sparkblitz.sps.scheduling;

import io.sparkblitz.sps.models.Activity;
import io.sparkblitz.sps.models.Project;

import java.util.Set;
import java.util.TreeSet;

public class PipeLine {

    private final Project project;
   private Set<Schedule.Entry> entries;

    public PipeLine(Project project){
        this.project = project;
        this.entries = new TreeSet<>();
    }

    public Project getProject() {
        return project;
    }

    public Set<Schedule.Entry> getEntries() {
        return entries;
    }

    public void put(int start, Activity activity) {
        Schedule.Entry todo = getOrCreate(activity);
        todo.setStart(start);
        this.entries.add(todo);
    }

    private Schedule.Entry getOrCreate(Activity activity) {
        return this.entries.stream().filter(t -> t.getId().equals(activity.getId())).findFirst().orElse(Schedule.createEntry(activity));
    }
}
