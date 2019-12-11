package edu.networks.project.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import edu.networks.project.R;
import edu.networks.project.messages.LoginRequest;
import edu.networks.project.messages.LoginResponse;
import edu.networks.project.services.LoginService;

public class LoginActivity extends AppCompatActivity {

    private final String SHARED_PREFERENCES = getClass().getName();

    private EditText usernameText, passwordText, allFields[];
    private CheckBox rememberMeCheck;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linkUI();
        loadFields();
    }

    public void login(View view) {
        if (!validateFields())
            return;
        new SubmitRequest().execute(buildRequest());
        if (rememberMeCheck.isChecked())
            saveFields();
    }

    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivityForResult(intent, 0);
    }

    private void loadFields() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        usernameText.setText(sharedPreferences.getString("username", ""));
        passwordText.setText(sharedPreferences.getString("password", ""));
        if (!usernameText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty())
            rememberMeCheck.setChecked(true);
    }

    private void saveFields() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", usernameText.getText().toString());
        editor.putString("password", passwordText.getText().toString());
        editor.apply();
    }

    private class SubmitRequest extends AsyncTask<LoginRequest, Void, LoginResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.requestFocus();
            for (EditText field : allFields)
                field.setEnabled(true);
        }

        @Override
        protected LoginResponse doInBackground(LoginRequest... requests) {
            LoginRequest request = requests[0];
            LoginResponse response = LoginService.execute(request);
            return response;
        }

        @Override
        protected void onPostExecute(LoginResponse response) {
            super.onPostExecute(response);
            progressBar.setVisibility(View.GONE);
            for (EditText field : allFields)
                field.setEnabled(true);
            if (response.status == 0) {
                Snackbar.make(progressBar, "an error occurred", Snackbar.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    private LoginRequest buildRequest() {
        LoginRequest request = new LoginRequest();
        request.username = usernameText.getText().toString();
        request.password = passwordText.getText().toString();
        return request;
    }

    private boolean validateFields() {
        for (EditText field : allFields)
            if (field.getText().toString().isEmpty()) {
                field.setError("field cannot be empty");
                return false;
            }
        return true;
    }

    private void linkUI() {
        usernameText = findViewById(R.id.txt_username);
        passwordText = findViewById(R.id.txt_password);
        allFields = new EditText[]{usernameText, passwordText};
        rememberMeCheck = findViewById(R.id.check_rememberMe);
        progressBar = findViewById(R.id.progressBar);
    }
}
