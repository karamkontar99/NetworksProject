package edu.networks.project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import edu.networks.project.R;
import edu.networks.project.notification.FileProgressNotification;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FileProgressNotification notification = new FileProgressNotification(getApplicationContext(), "First File", 1000, true);
        Timer timer = new Timer();
        timer.schedule(new MyTask(notification), 0, 3000);

        FileProgressNotification notification1 = new FileProgressNotification(getApplicationContext(), "Second File", 1000, true);
        Timer timer1 = new Timer();
        timer1.schedule(new MyTask(notification1), 0, 3000);

    }


    class MyTask extends TimerTask {

        private final FileProgressNotification notification;
        private int size = 0;
        private int maxSize = 1000;

        MyTask(FileProgressNotification notification) {
            this.notification = notification;
        }

        public void run() {
            size += 10;
            notification.updateNotification(size);

            if (size >= maxSize)
                notification.finishNotification(new File(""));
        }
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
