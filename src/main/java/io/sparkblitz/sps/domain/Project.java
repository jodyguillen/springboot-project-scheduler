package io.sparkblitz.sps.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.sparkblitz.sps.scheduling.TimeUnit;

import javax.persistence.*;
import java.util.*;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String timeUnit = TimeUnit.DAY.getName();

    @JsonFormat(pattern="yyyyMMdd")
    private Date startDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Activity> activities = new LinkedHashSet<>();

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = Optional.ofNullable(timeUnit)
        .map(unit -> TimeUnit.from(unit))
        .map(unit -> unit.getName())
        .orElse(TimeUnit.DAY.getName());
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id.equals(project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
