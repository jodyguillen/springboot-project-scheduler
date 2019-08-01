package io.sparkblitz.sps.models;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Optional;

@Embeddable
public class ActivityDependencyId implements Serializable {
    private Integer rootTaskId;
    private Integer nextTaskId;

    public Integer getRootTaskId() {
        return rootTaskId;
    }

    public void setRootTaskId(Integer rootTaskId) {
        this.rootTaskId = rootTaskId;
    }

    public Integer getNextTaskId() {
        return nextTaskId;
    }

    public void setNextTaskId(Integer nextTaskId) {
        this.nextTaskId = nextTaskId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        ActivityDependencyId other = Optional.of(obj)
                .filter(o -> o instanceof ActivityDependencyId)
                .map(o -> (ActivityDependencyId) o)
                .filter(o -> null != rootTaskId)
                .filter(o -> null != nextTaskId)
                .orElse(null);

        if (null == other) {
            return false;
        }

        return other.rootTaskId.intValue() == this.rootTaskId.intValue()
                && other.nextTaskId.intValue() == this.nextTaskId.intValue();
    }
}
