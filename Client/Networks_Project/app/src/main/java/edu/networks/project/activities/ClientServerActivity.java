package edu.networks.project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import edu.networks.project.models.Document;

public class ClientServerActivity extends AppCompatActivity {

    private List<Document> documents = new ArrayList<>();
    private DocumentAdapter adapter;
    private SearchView searchView;
    private ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_server);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
//            startActivityForResult(intent, CHOOSE_FILE_REQUEST_CODE);
        });

//        new PopulateList().execute();
    }

//    private class PopulateList extends AsyncTask<Void, Void, List<Document>> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.requestFocus();
//        }
//
//        @Override
//        protected LoginResponse doInBackground(Void... voids) {
//            LoginRequest request = requests[0];
//            LoginResponse response = LoginService.execute(request);
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(LoginResponse response) {
//            super.onPostExecute(response);
//            progressBar.setVisibility(View.GONE);
//            for (AutoCompleteTextView field : allFields)
//                field.setEnabled(true);
//            if (response.status == 0) {
//                Snackbar.make(progressBar, "an error occurred", Snackbar.LENGTH_LONG).show();
//            }
//            else {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        }
//    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CHOOSE_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
//            String filePath = data.getDataString();
//            File file = new File(filePath);
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Choose file name");
//
//            final EditText input = new EditText(builder.getContext());
//            input.setInputType(InputType.TYPE_CLASS_TEXT);
//            input.setText(file.getName());
//            builder.setView(input);
//
//            builder.setPositiveButton("OK", (dialog, i) -> {
//                String fileName = input.getText().toString();
//                if (!file.getName().equals(fileName)) {
//                    if (fileName.isEmpty()) {
//                        input.setError("cannot be empty");
//                        return;
//                    }
//                    if (fileManager.hasFile(fileName)) {
//                        input.setError("file name already exists");
//                        return;
//                    }
//                    try {
//                        fileManager.addFile(fileName, file);
//                        mAllFiles.add(0, fileManager.getFile(fileName));
//                        mSearchView.setQuery("", true);
//                    } catch (Exception e) {
//                        Snackbar.make(mSearchView, "an error occurred", Snackbar.LENGTH_SHORT).show();
//                    }
//                }
//                dialog.dismiss();
//            });
//
//            builder.setNegativeButton("Cancel", (dialog, i) -> {
//                dialog.dismiss();
//            });
//
//            builder.show();
//        }
//    }


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
