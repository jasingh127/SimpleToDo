package com.codepath.jasvinders.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private String taskName;
    private int taskPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        taskName = getIntent().getStringExtra("taskName");
        taskPosition = getIntent().getIntExtra("taskPosition", 0);
        EditText editText = (EditText) findViewById(R.id.editItem);
        editText.setText(taskName);
        editText.setSelection(taskName.length());
    }

    public void onSubmit(View v) {
        EditText editText = (EditText) findViewById(R.id.editItem);
        taskName = editText.getText().toString();
        Intent data = new Intent();
        data.putExtra("taskName", taskName);
        data.putExtra("taskPosition", taskPosition);
        setResult(RESULT_OK, data);
        finish();
    }

}
