package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class Goal {


    private String id;
    private String owner;

    private String name;
    private String description;
    private Interval interval;
    private Boolean done;
    private String creationTime;
    private String endTime;
    private String deadlineTime;
    private ArrayList<Goal> subGoalsArrayList = new ArrayList<>();


    public Goal(String owner, String name, String description, Interval interval, Boolean done, LocalDate deadlineTime) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.interval = interval;
        this.done = done;

        LocalDateTime tempTime = deadlineTime.atStartOfDay();

        DateTimeFormatter formatterDeadlineTime = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.deadlineTime = tempTime.format(formatterDeadlineTime);

        DateTimeFormatter formatterCreationTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.creationTime = LocalDateTime.now().format(formatterCreationTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public void setDeadlineTime(LocalDate deadlineTime){
        LocalDateTime tempTime = deadlineTime.atStartOfDay();

        DateTimeFormatter formatterDeadlineTime = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.deadlineTime = tempTime.format(formatterDeadlineTime);
    }

    public ArrayList<Goal> getSubGoalsArrayList() {
        return subGoalsArrayList;
    }

    public void setSubGoalsArrayList(ArrayList<Goal> subGoalsArrayList) {
        this.subGoalsArrayList = subGoalsArrayList;
    }


    public static class SortByDate implements Comparator<Goal> {
        @Override
        public int compare(Goal a, Goal b) {
            return a.deadlineTime.compareTo(b.deadlineTime);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}

