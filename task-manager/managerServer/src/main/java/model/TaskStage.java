package model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class TaskStage {
    @Expose private int id;
    @Expose private String name;
    @Expose private String description;
    @Expose private List<Task> tasks;

    public TaskStage(){

    }

    public List<Task> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}
