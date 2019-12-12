package edu.networks.project.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.networks.project.BuildConfig;
import edu.networks.project.R;
import edu.networks.project.files.FileManager;

public class LocalFilesActivity extends AppCompatActivity {

    private final int CHOOSE_FILE_REQUEST_CODE = 100;

    private FileManager fileManager;

    private List<File> mAllFiles, mFiles;
    private FilesAdapter mAdapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_files);

        fileManager = new FileManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAllFiles = mFiles = fileManager.getAllFiles();

        RecyclerView recyclerView = findViewById(R.id.recycleview);
        mSearchView = findViewById(R.id.searchView);

        mAdapter = new FilesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(LocalFilesActivity.this));
        recyclerView.setAdapter(mAdapter);

        setupSearchView();

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

    }

    private class FileViewHolder extends RecyclerView.ViewHolder {
        private TextView mFileName, mFilePath;

        public FileViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(LocalFilesActivity.this).inflate(R.layout.file_list_item, parent, false));
            mFileName = itemView.findViewById(R.id.textView_file_name);
            mFilePath = itemView.findViewById(R.id.textView_file_path);
        }

        public void bind(File file) {
            mFileName.setText(file.getName());
            mFilePath.setText(file.getAbsolutePath());

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
                intent.setData(uri);
                Intent chooserIntent = Intent.createChooser(intent, "Choose an application to open");
                startActivity(chooserIntent);
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
