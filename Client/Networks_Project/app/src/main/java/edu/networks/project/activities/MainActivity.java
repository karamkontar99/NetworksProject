package edu.networks.project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import edu.networks.project.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void clientServer(View view) {
        startActivity(new Intent(MainActivity.this, ClientServerActivity.class));
    }

    public void peerToPeer(View view) {
        startActivity(new Intent(MainActivity.this, PeerToPeerActivity.class));
    }

    public void localFiles(View view) {
        startActivity(new Intent(MainActivity.this, LocalFilesActivity.class));
    }
}
