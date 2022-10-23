package com.fptu.prm392;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    private EditText job_id;
    private EditText job_name;
    private EditText job_status;
    private EditText job_desc;

    private Button add_btn;
    private Button del_btn;
    private Button update_btn;
    private Button list_btn;

    private RecyclerView list_result;

    private List<Job> mListJob;
    private JobAdapter jobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        Init();
        jobAdapter = new JobAdapter();

        add_btn.setOnClickListener(view -> addJob());
        update_btn.setOnClickListener(view -> updateJob());
        del_btn.setOnClickListener(view -> deleteJob());
        list_btn.setOnClickListener(view -> listJobs());

    }

    void Init() {
        job_id = findViewById(R.id.input_id);
        job_name = findViewById(R.id.input_name);
        job_status = findViewById(R.id.input_status);
        job_desc = findViewById(R.id.input_description);

        add_btn = findViewById(R.id.btn_add);
        del_btn = findViewById(R.id.btn_delete);
        update_btn = findViewById(R.id.btn_update);
        list_btn = findViewById(R.id.btn_list);

        list_result = findViewById(R.id.item_recyclerview);
        list_result.addItemDecoration(new DividerItemDecoration(list_result.getContext(), DividerItemDecoration.HORIZONTAL));
    }

    // insert new record
    private void addJob() {
        String text_id = job_id.getText().toString();
        if (text_id.isEmpty() || text_id.trim().isEmpty()) {
            Toast.makeText(this, "Id cannot be null or empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        String text_name = job_name.getText().toString().trim();
        String text_status = job_status.getText().toString().trim();
        String text_desc = job_desc.getText().toString().trim();
        Job job = Database.getInstance(this).Dao().getJobById(text_id);
        if (job != null) {
            try {
                job.setName(text_name);
                job.setStatus(text_status);
                job.setDescription(text_desc);
                Database.getInstance(this).Dao().update(job);
                Toast.makeText(this, "Id existed! Update job with id " + text_id + " successfully!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Some errors occurs! Update failed.", Toast.LENGTH_SHORT).show();
            }
        } else {
            try {
                job = new Job(text_id, text_name, text_status, text_desc);
                Database.getInstance(this).Dao().insert(job);
                Toast.makeText(this, "Insert job successfully!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Some errors occurs! Insert failed.", Toast.LENGTH_SHORT).show();
            }
        }
        hideKeyboard();
        job_id.setText("");
        job_name.setText("");
        job_status.setText("");
        job_desc.setText("");
    }

    // delete record
    private void deleteJob() {
        String text_id = job_id.getText().toString();
        if (text_id.isEmpty() || text_id.trim().isEmpty()) {
            Toast.makeText(this, "Id is required to delete!", Toast.LENGTH_SHORT).show();
            return;
        }
        Job job = Database.getInstance(this).Dao().getJobById(text_id);
        if (job == null) {
            Toast.makeText(this, "Id is not matched any job in database!", Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete job")
                .setMessage("Job at id " + job_id.getText().toString().trim() + "\nThis cannot be undo!")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    try {
                        Database.getInstance(this).Dao().delete(job);
                        Toast.makeText(this, "Delete job with id " + text_id + " successfully!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Some errors occurs! Delete failed.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
        hideKeyboard();
        job_id.setText("");
        job_name.setText("");
        job_status.setText("");
        job_desc.setText("");
    }

    // update record
    private void updateJob() {
        String text_id = job_id.getText().toString();
        if (text_id.isEmpty() || text_id.trim().isEmpty()) {
            Toast.makeText(this, "Id is required to update!", Toast.LENGTH_SHORT).show();
            return;
        }
        Job job = Database.getInstance(this).Dao().getJobById(text_id);
        if (job == null) {
            Toast.makeText(this, "Id is not matched any job in database!", Toast.LENGTH_SHORT).show();
            return;
        }
        String text_name = job_name.getText().toString().trim();
        String text_status = job_status.getText().toString().trim();
        String text_desc = job_desc.getText().toString().trim();
        job.setName(text_name);
        job.setStatus(text_status);
        job.setDescription(text_desc);
        try {
            Database.getInstance(this).Dao().update(job);
            Toast.makeText(this, "Update job with id " + text_id + " successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Some errors occurs! Update failed.", Toast.LENGTH_SHORT).show();
        }
        hideKeyboard();
        job_id.setText("");
        job_name.setText("");
        job_status.setText("");
        job_desc.setText("");
    }

    // list records
    private void listJobs() {
        String text_id = "%" + job_id.getText().toString() + "%";
        String text_name = "%" + job_name.getText().toString().trim() + "%";
        List<Job> searchResult = Database.getInstance(this).Dao().getJobs(text_id, text_name);
        jobAdapter.setData(searchResult);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        list_result.setLayoutManager(linearLayoutManager);
        list_result.setAdapter(jobAdapter);
        Toast.makeText(this, searchResult.size() + " record(s) found!", Toast.LENGTH_SHORT).show();
        hideKeyboard();
    }

    private void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}