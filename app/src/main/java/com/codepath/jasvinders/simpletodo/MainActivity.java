package com.codepath.jasvinders.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String todoFileName = "todoTasks.json";
    ArrayList<ToDoTask> tasks;
    CustomTaskAdaptor tasksAdaptor;
    ListView lvItems;
    private final int REQUEST_CODE = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        tasks = new ArrayList<>();
        readItems();
        Collections.sort(tasks, new ToDoTask());
        tasksAdaptor = new CustomTaskAdaptor(this, tasks);
        lvItems.setAdapter(tasksAdaptor);
        setupListViewListener();
        setupEditClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds tasks to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        tasks.add(new ToDoTask(itemText, ToDoTask.priorities[0]));
        Collections.sort(tasks, new ToDoTask());
        tasksAdaptor.notifyDataSetChanged();
        etNewItem.setText("");
        writeItems();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tasks.remove(position);
                tasksAdaptor.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private void setupEditClickListener() {
       lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               ToDoTask value = (ToDoTask) parent.getItemAtPosition(position);
               Toast.makeText(getApplicationContext(), value.toString(), Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
               intent.putExtra("taskPosition", position);
               intent.putExtra("taskDesc", value.desc);
               intent.putExtra("taskPriority", value.priority);
               startActivityForResult(intent, REQUEST_CODE);
           }
       });
    }

    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK && reqCode == REQUEST_CODE) {
            String taskName = data.getExtras().getString("taskName");
            String taskPriority = data.getExtras().getString("taskPriority");
            int position = data.getExtras().getInt("taskPosition");
            tasks.set(position, new ToDoTask(taskName, taskPriority));
            Collections.sort(tasks, new ToDoTask());
            tasksAdaptor.notifyDataSetChanged();
            writeItems();
            Toast.makeText(getApplicationContext(), taskName + " edited", Toast.LENGTH_SHORT).show();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, todoFileName);
        try {
            tasks = new ArrayList<ToDoTask>();
            List<String> lines = FileUtils.readLines(todoFile);
            for (String line : lines)
                tasks.add(new ToDoTask(line));
        } catch (IOException e) {
            tasks = new ArrayList<ToDoTask>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, todoFileName);
        try {
            List<String> lines = new ArrayList<>();
            for (ToDoTask task : tasks)
                lines.add(task.toJson());
            FileUtils.writeLines(todoFile, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
