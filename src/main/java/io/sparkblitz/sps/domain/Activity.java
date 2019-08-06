package io.sparkblitz.sps.domain;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public class Activity {

    @Id
    private String code;
    private Integer duration;

    @Transient
    private int start=0;

    @Transient
    private int finish=0;

    @ElementCollection
    private Set<String> next = new HashSet<>();

    public Activity() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Set<String> getNext() {
        return next;
    }

    public void setNext(Set<String> next) {
        this.next = next;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        if(start > this.start) {
            this.start = start;
            this.finish = this.start + (this.duration == null ? 0 : this.duration) - 1;
        }
    }

    public int getFinish() {
        return finish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return code.equals(activity.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
