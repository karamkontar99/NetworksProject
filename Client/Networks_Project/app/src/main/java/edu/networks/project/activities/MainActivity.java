package edu.networks.project.activities;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import edu.networks.project.R;
import edu.networks.project.server.P2PServerService;

public class MainActivity extends AppCompatActivity {

    @Inject
    P2PServerService p2PServerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupP2P();
    }

    private void setupP2P () {
        while (true) {

        }
    }
}
