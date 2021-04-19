package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Goal {

    private String name;
    private String description;
    private Interval interval;
    private Boolean done;
    private LocalDateTime creationTime;
    private LocalDateTime endTime;
    private LocalDateTime deadlineTime;
    private ArrayList<Goal> subGoalsArrayList = new ArrayList<>();


    public Goal(String name, String description, Interval interval, Boolean done, LocalDate deadlineTime) {
        this.name = name;
        this.description = description;
        this.interval = interval;
        this.done = done;
        this.creationTime = LocalDateTime.now();
        this.deadlineTime = deadlineTime.atStartOfDay();
//        this.deadlineTime = java.sql.Date.valueOf(deadlineTime);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(LocalDateTime deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public ArrayList<Goal> getSubGoalsArrayList() {
        return subGoalsArrayList;
    }

    public void setSubGoalsArrayList(ArrayList<Goal> subGoalsArrayList) {
        this.subGoalsArrayList = subGoalsArrayList;
    }

    @Override
    public String toString() {
        return name;
    }
}

