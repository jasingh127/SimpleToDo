package com.codepath.jasvinders.simpletodo;

import android.graphics.Color;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by jasvinder.s on 11/23/2015.
 */
public class ToDoTask implements Comparator<ToDoTask> {
    public String desc;
    public String priority;
    static public final String [] priorities = {"LOW", "MEDIUM", "HIGH"};

    public ToDoTask() {
    }

    public ToDoTask(String desc_, String priority_) {
        desc = desc_;
        priority = priority_;
    }

    public ToDoTask(String taskString) {
        ToDoTask todoTask = new Gson().fromJson(taskString, ToDoTask.class);
        desc = todoTask.desc;
        priority = todoTask.priority;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String toString() {
        // return desc + ": " + priority;
        return toJson();
    }

    public static int priorityToColor(String p) {
        switch(p) {
            case "LOW":
                return Color.GREEN;
            case "MEDIUM":
                return Color.YELLOW;
            case "HIGH":
                return Color.RED;
        }
        return Color.GRAY;
    }

    public static int priorityToId(String p) {
        switch(p) {
            case "LOW":
                return 0;
            case "MEDIUM":
                return 1;
            case "HIGH":
                return 2;
        }
        return 0;
    }

    public int compare(ToDoTask t1, ToDoTask t2) {
        return priorityToId(t2.priority) - priorityToId(t1.priority);
    }

}
