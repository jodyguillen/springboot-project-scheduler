package io.sparkblitz.sps.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
public class Activity implements Comparable<Activity> {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private int duration=0;

    @Transient
    private int start=0;

    @Transient
    private int end=0;

    @ManyToOne
    @JoinColumn
    private Project project;

    @Transient
    private List<Activity> next;

    public Activity() {
    }

    public Activity(String name, Project project) {
        this.name = name;
        this.project = project;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
        Activity other = Optional.of(obj)
                .filter(o -> o instanceof Activity)
                .map(o -> (Activity) o)
                .orElse(null);

        if(null == other) {
            return false;
        }

        return other.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(this.getId(), o.getId());
    }

    public String toString() {
        return String.format("%s[ %d ]",getName(), getDuration() );
    }

    public List<Activity> getNext() {
        return next;
    }

    public void setNext(List<Activity> next) {
        this.next = next;
    }
}
