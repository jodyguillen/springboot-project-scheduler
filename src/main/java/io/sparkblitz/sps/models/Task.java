package io.sparkblitz.sps.models;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn
    private Project project;

    public Task() {
    }

    public Task(String name, Project project) {
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
}
