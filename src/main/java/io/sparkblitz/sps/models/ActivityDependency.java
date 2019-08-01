package io.sparkblitz.sps.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class ActivityDependency {

    @EmbeddedId
    private ActivityDependencyId id = new ActivityDependencyId();

    @ManyToOne
    @MapsId("rootTaskId")
    private Activity rootActivity;

    @ManyToOne
    @MapsId("nextTaskId")
    private Activity nextActivity;

    public ActivityDependency() {
    }

    public ActivityDependency(Activity rootActivity) {
        this.rootActivity = rootActivity;
    }

    public ActivityDependency(Activity rootActivity, Activity nextActivity) {
        this.rootActivity = rootActivity;
        this.nextActivity = nextActivity;
    }

    public ActivityDependencyId getId() {
        return id;
    }

    public void setId(ActivityDependencyId id) {
        this.id = id;
    }

    public Activity getRootActivity() {
        return rootActivity;
    }

    public void setRootActivity(Activity rootActivity) {
        this.rootActivity = rootActivity;
    }

    public Activity getNextActivity() {
        return nextActivity;
    }

    public void setNextActivity(Activity nextActivity) {
        this.nextActivity = nextActivity;
    }
}
