package io.sparkblitz.sps.dto;

import io.sparkblitz.sps.models.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ActivityDTO implements Comparable<ActivityDTO> {
    private int id;
    private String name;
    private int duration;
    private int start = 1;
    private int finish = 1;
    private List<ActivityDTO> next;

    @FunctionalInterface
    public interface NextGrabber {
        List<Activity> grab(Activity activity);
    }

    public ActivityDTO(Activity activity) {
        this.id = activity.getId();
        this.duration = Optional.of(activity.getDuration()).orElse(0);
        this.name = activity.getName();
        this.next = new ArrayList<>();
    }

    public ActivityDTO(Activity activity, List<Activity> next) {
        this(activity);
        this.next = next.stream().map(a -> new ActivityDTO(a)).collect(Collectors.toList());
    }

    public ActivityDTO(Activity activity, NextGrabber nextGrabber) {
        this(activity, nextGrabber != null ? nextGrabber.grab(activity) : new ArrayList<>());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if(start > this.start) {
            this.start = start;
            this.finish = computeFinish();
        }
    }

    public int getFinish() {
        return finish;
    }

    public List<ActivityDTO> getNext() {
        return next;
    }

    public void addNext(ActivityDTO next) {
        this.next.add(next);
    }

    @Override
    public int compareTo(ActivityDTO o) {
        return Integer.compare(this.getId(), o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityDTO that = (ActivityDTO) o;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private int computeFinish(){
        return this.start + this.duration - 1;
    }

    public Activity toModel(){
        Activity activity = new Activity();
        activity.setDuration(this.getDuration());
        activity.setName(this.getName());
        return activity;
    }
}
