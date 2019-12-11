package edu.networks.project.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.networks.project.R;
import edu.networks.project.files.FileManager;

public class ClientServerActivity extends AppCompatActivity {

    private final int CHOOSE_FILE_REQUEST_CODE = 100;

    private List<File> mAllFiles, mFiles;
    private FilesAdapter mAdapter;
    private SearchView mSearchView;
    
    private FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_server);
        fileManager = new FileManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAllFiles = fileManager.getAllFiles();

        RecyclerView recyclerView = findViewById(R.id.recycleview);
        mSearchView = findViewById(R.id.searchView);

        mAdapter = new FilesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        setupSearchView();

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
                if (!file.getName().equals(fileName)) {
                    if (fileName.isEmpty()) {
                        input.setError("cannot be empty");
                        return;
                    }
                    if (fileManager.hasFile(fileName)) {
                        input.setError("file name already exists");
                        return;
                    }
                    try {
                        fileManager.addFile(fileName, file);
                        mAllFiles.add(0, fileManager.getFile(fileName));
                        mSearchView.setQuery("", true);
                    } catch (Exception e) {
                        Snackbar.make(mSearchView, "an error occurred", Snackbar.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
            });

            builder.setNegativeButton("Cancel", (dialog, i) -> {
                dialog.dismiss();
            });

            builder.show();
        }
    }

    private void setupSearchView(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mFiles = mAllFiles.stream().filter(file -> query.isEmpty() || file.getName().toLowerCase().equals(query.toLowerCase())).collect(Collectors.toList());
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });

        mSearchView.setQuery("", true);
    }


    private class FileViewHolder extends RecyclerView.ViewHolder {
        private TextView mFileName, mFilePath;

        public FileViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(ClientServerActivity.this).inflate(R.layout.file_list_item, parent, false));
            mFileName = itemView.findViewById(R.id.textView_file_name);
            mFilePath = itemView.findViewById(R.id.textView_file_path);
        }

        public void bind(File file) {
            mFileName.setText(file.getName());
            mFilePath.setText(file.getAbsolutePath());

            itemView.setOnLongClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.fromFile(file));
                Intent chooserIntent = Intent.createChooser(intent, "Choose an application to open");
                startActivity(chooserIntent);
                return true;
            });

            itemView.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClientServerActivity.this);
                builder.setTitle("Choose an action");
                builder.setMessage("What do you want to do with file");

                final EditText input = new EditText(builder.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(file.getName());
                builder.setView(input);

                builder.setPositiveButton("OK", (dialog, i) -> {
                    String fileName = input.getText().toString();
                    if (!file.getName().equals(fileName)) {
                        if (fileName.isEmpty()) {
                            input.setError("cannot be empty");
                            return;
                        }
                        if (fileManager.hasFile(fileName)) {
                            input.setError("file name already exists");
                            return;
                        }
                        try {
                            fileManager.renameFile(file.getName(), fileName);
                            mAllFiles.set(mAllFiles.indexOf(file), fileManager.getFile(fileName));
                            mSearchView.setQuery("", true);
                        } catch (Exception e) {
                            Snackbar.make(mSearchView, "an error occurred", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    dialog.dismiss();
                });

                builder.setNegativeButton("Delete", (dialog, i) -> {
                    fileManager.removeFile(file.getName());
                    mAllFiles.remove(file);
                    mSearchView.setQuery("", true);
                    dialog.dismiss();
                });

                builder.show();
            });
        }
    }

    private class FilesAdapter extends RecyclerView.Adapter<FileViewHolder> {

        @Override
        public FileViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            return new FileViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(FileViewHolder holder, int i) {
            holder.bind(mFiles.get(i));
        }

        @Override
        public int getItemCount() {
            return mFiles.size();
        }
    }

}
