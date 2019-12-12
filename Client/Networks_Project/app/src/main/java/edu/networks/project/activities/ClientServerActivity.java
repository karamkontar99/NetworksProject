package edu.networks.project.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.networks.project.DocumentAdapter;
import edu.networks.project.R;
import edu.networks.project.files.FileManager;
import edu.networks.project.messages.FileDownloadRequest;
import edu.networks.project.messages.FileDownloadResponse;
import edu.networks.project.messages.FileListRequest;
import edu.networks.project.messages.FileListResponse;
import edu.networks.project.messages.FileUploadRequest;
import edu.networks.project.messages.FileUploadResponse;
import edu.networks.project.models.Document;
import edu.networks.project.notification.FileProgressNotification;
import edu.networks.project.services.FileDownloadService;
import edu.networks.project.services.FileListService;
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

        FileListRequest request = new FileListRequest();
        new LoadDocuments().execute(request);
    }

    private class LoadDocuments extends AsyncTask<FileListRequest, Void, FileListResponse> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.requestFocus();
        }

        @Override
        protected FileListResponse doInBackground(FileListRequest... requests) {
            FileListRequest request = requests[0];
            FileListResponse response = FileListService.execute(request);
            return response;
        }

        @Override
        protected void onPostExecute(FileListResponse response) {
            super.onPostExecute(response);
            progressBar.setVisibility(View.GONE);
            if (response.status == 0)
                return;
            Log.e("SOCKETS", response.names.toString() + "   " + response.sizes.toString());
            documents = new ArrayList<>();
            for (int i = 0; i < response.names.size(); i++)
                documents.add(new Document(response.names.get(i), response.sizes.get(i), fileManager.hasFile(response.names.get(i))));
            adapter.setDocuments(documents);
        }
    }

    public File importFile(Uri uri, String oldFileName, String newFileName) throws IOException {
        File tempFile = fileManager.getFile(oldFileName);
        InputStream inputStream = getContentResolver().openInputStream(uri);
        if (inputStream == null)
            throw new IOException("Unable to obtain input stream from URI");
        FileUtils.copyInputStreamToFile(inputStream, tempFile);
        if (oldFileName.equals(newFileName))
            return tempFile;
        File file = fileManager.getFile(newFileName);
        FileUtils.copyFile(tempFile, file);
        FileUtils.deleteQuietly(tempFile);
        return file;
    }

    private String getFileName(Uri uri) throws IllegalArgumentException {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            throw new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }
        cursor.moveToFirst();
        String fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
        cursor.close();
        return fileName;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            String oldFileName = getFileName(data.getData());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose file name");

            final EditText input = new EditText(builder.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setText(oldFileName);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, i) -> {
                try {
                    String newFileName = input.getText().toString();
                    if (newFileName.isEmpty()) {
                        Snackbar.make(progressBar, "file name cannot be empty", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    if (fileManager.hasFile(newFileName) || documents.stream().anyMatch(doc -> doc.getName().equals(newFileName))) {
                        Snackbar.make(progressBar, "file with same name exists", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    File file;
                    try {
                        file = importFile(data.getData(), oldFileName, newFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Snackbar.make(progressBar, "could not load file", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    FileProgressNotification notification = new FileProgressNotification(getApplicationContext(), newFileName, false);
                    FileUploadRequest request = new FileUploadRequest();
                    request.fileName = newFileName;
                    request.fileSize = (int) file.length();
                    request.data = FileUtils.readFileToByteArray(file);
                    new FileUploadThread(request, notification).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(progressBar, "an error occurred", Snackbar.LENGTH_LONG).show();
                }
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
                notification.finishNotification();
            else
                notification.failNotification();
        }
    }
    private class FileDownloadThread extends Thread {
        private final FileDownloadRequest request;
        private final FileProgressNotification notification;

        FileDownloadThread(FileDownloadRequest request, FileProgressNotification notification) {
            this.request = request;
            this.notification = notification;
        }

        @Override
        public void run() {
            super.run();

            FileDownloadResponse response = FileDownloadService.execute(request, notification::updateNotification);
            if (response.status != 1) {
                notification.failNotification();
                return;
            }
            try {
                fileManager.writeFile(response.fileName, response.data);
            } catch (Exception e) {
                notification.failNotification();
                return;
            }
            notification.finishNotification();
        }
    }

    private void setupAdapter() {
        adapter.setOnDocumentClickListener(((document, index) -> {
            if (document.isDownloaded()) {
                Snackbar.make(progressBar, "file already downloaded", Snackbar.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Download File");

            builder.setPositiveButton("Yes", (dialog, i) -> {
                FileDownloadRequest request = new FileDownloadRequest();
                request.fileName = document.getName();
                FileProgressNotification notification = new FileProgressNotification(getApplicationContext(), document.getName(), true);
                new FileDownloadThread(request, notification).start();
                dialog.dismiss();
            });

            builder.setNegativeButton("No", (dialog, i) -> {
                dialog.dismiss();
            });

            builder.show();
        }));
    }


    private void setupSearchView(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                List<Document> filteredDocuments = new ArrayList<>();
                for (Document document : documents)
                    if (document.getName().toLowerCase().contains(query.toLowerCase()))
                        filteredDocuments.add(document);
                adapter.setDocuments(filteredDocuments);
                return true;
            }
        });
        searchView.setQuery("", true);
    }

}
