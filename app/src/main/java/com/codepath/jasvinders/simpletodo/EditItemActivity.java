package com.codepath.jasvinders.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Logger;

public class EditItemActivity extends AppCompatActivity {
    private int taskPosition;
    Spinner prioritySpinner;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String taskDesc = getIntent().getStringExtra("taskDesc");
        String taskPriority = getIntent().getStringExtra("taskPriority");
        taskPosition = getIntent().getIntExtra("taskPosition", 0);
        EditText editText = (EditText) findViewById(R.id.editTaskName);
        editText.setText(taskDesc);
        editText.setSelection(taskDesc.length());
        prioritySpinner = (Spinner) findViewById(R.id.prioritySpinner);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ToDoTask.priorities);
        prioritySpinner.setAdapter(adapter);
        prioritySpinner.setSelection(ToDoTask.priorityToId(taskPriority));
    }

    public void onSubmit(View v) {
        EditText editText = (EditText) findViewById(R.id.editTaskName);
        String taskName = editText.getText().toString();
        Intent data = new Intent();
        data.putExtra("taskName", taskName);
        String taskPriority = prioritySpinner.getSelectedItem().toString();
        data.putExtra("taskPriority", taskPriority);
        data.putExtra("taskPosition", taskPosition);
        setResult(RESULT_OK, data);
        finish();
    }

}
