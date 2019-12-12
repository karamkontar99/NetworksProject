package edu.networks.project.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.networks.project.DocumentAdapter;
import edu.networks.project.R;
import edu.networks.project.files.FileManager;
import edu.networks.project.messages.FileUploadRequest;
import edu.networks.project.messages.FileUploadResponse;
import edu.networks.project.models.Document;
import edu.networks.project.notification.FileProgressNotification;
import edu.networks.project.services.FileUploadService;

public class ClientServerActivity extends AppCompatActivity {
    private final int CHOOSE_FILE_REQUEST_CODE = 100;

    private List<Document> documents = new ArrayList<>();
    private DocumentAdapter adapter;
    private SearchView searchView;
    private ProgressBar progressBar;

    private FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_server);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fileManager = new FileManager(getApplicationContext());

        progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        searchView = findViewById(R.id.searchView);
        adapter = new DocumentAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setupSearchView();

        setupAdapter();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, CHOOSE_FILE_REQUEST_CODE);
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            String filePath = data.getDataString();
            File file = new File(filePath);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose file name");

            final EditText input = new EditText(builder.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setText(file.getName());
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, i) -> {
                String fileName = input.getText().toString();

                dialog.dismiss();
            });

            builder.setNegativeButton("Cancel", (dialog, i) -> {
                dialog.dismiss();
            });

            builder.show();
        }
    }

    private class FileUploadThread extends Thread {
        private final FileUploadRequest request;
        private final FileProgressNotification notification;

        FileUploadThread(FileUploadRequest request, FileProgressNotification notification) {
            this.request = request;
            this.notification = notification;
        }

        @Override
        public void run() {
            super.run();

            FileUploadResponse response = FileUploadService.execute(request, notification::updateNotification);
            if (response.status == 1)
                notification.finishNotification(null);
            else
                notification.failNotification();
        }
    }

    private void setupAdapter() {
        adapter.setOnDocumentClickListener(((document, index) -> {

        }));
    }


    private void setupSearchView(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Document> filteredDocuments = documents.stream().filter(file -> query.isEmpty() || file.getName().toLowerCase().equals(query.toLowerCase())).collect(Collectors.toList());
                adapter.setDocuments(filteredDocuments);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                List<Document> filteredDocuments = documents.stream().filter(file -> query.isEmpty() || file.getName().toLowerCase().equals(query.toLowerCase())).collect(Collectors.toList());
                adapter.setDocuments(filteredDocuments);
                return true;
            }
        });
        searchView.setQuery("", true);
    }

}
