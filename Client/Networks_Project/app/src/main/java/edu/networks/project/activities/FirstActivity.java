package edu.networks.project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import edu.networks.project.MySocket;
import edu.networks.project.R;
import edu.networks.project.messages.LoginResponse;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        try {
            LoginResponse response = new LoginResponse();
            response.status = 1;
            byte[] bytes = response.serializeToByteArray();
            response = new LoginResponse();
            response.parseFromByteArray(bytes);
            Log.e("SOCKETS", "status " + response.status);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void submit(View view) {
        EditText input = findViewById(R.id.editText_server_ip);
        String serverIp = input.getText().toString();
        if (serverIp.isEmpty()) {
            input.setError("cannot be empty");
            return;
        }
        MySocket.setServerIp(serverIp);
        Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
