package com.codepath.jasvinders.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jasvinder.s on 11/24/2015.
 */
public class CustomTaskAdaptor extends ArrayAdapter<ToDoTask> {
    public CustomTaskAdaptor(Context context, ArrayList<ToDoTask> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToDoTask task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_task, parent, false);
        }
        // Lookup view for data population
        TextView taskDesc = (TextView) convertView.findViewById(R.id.taskDesc);
        TextView taskPriority = (TextView) convertView.findViewById(R.id.taskPriority);
        // Populate the data into the template view using the data object
        taskDesc.setText(task.desc);
        taskPriority.setText(task.priority);
        taskPriority.setTextColor(ToDoTask.priorityToColor(task.priority));
        // Return the completed view to render on screen
        return convertView;
    }
}
