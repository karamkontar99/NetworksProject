package edu.networks.project.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import edu.networks.project.R;
import edu.networks.project.messages.RegistrationRequest;
import edu.networks.project.messages.RegistrationResponse;
import edu.networks.project.services.RegistrationService;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameText, addressText, emailText, usernameText, passwordText, allFields[];
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        linkUI();
    }

    public void register(View view) {
        if (!validateFields())
            return;
        new SubmitRequest().execute(buildRequest());
    }

    private class SubmitRequest extends AsyncTask<RegistrationRequest, Void, RegistrationResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.requestFocus();
            for (EditText field : allFields)
                field.setEnabled(true);
        }

        @Override
        protected RegistrationResponse doInBackground(RegistrationRequest... requests) {
            RegistrationRequest request = requests[0];
            RegistrationResponse response = RegistrationService.execute(request);
            return response;
        }

        @Override
        protected void onPostExecute(RegistrationResponse response) {
            super.onPostExecute(response);
            progressBar.setVisibility(View.GONE);
            for (EditText field : allFields)
                field.setEnabled(true);
            if (response.status == 0) {
                Snackbar.make(progressBar, "an error occurred", Snackbar.LENGTH_LONG).show();
            }
            else {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private RegistrationRequest buildRequest() {
        RegistrationRequest request = new RegistrationRequest();
        request.name = nameText.getText().toString();
        request.address = addressText.getText().toString();
        request.email = emailText.getText().toString();
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
        nameText = findViewById(R.id.txt_name);
        addressText = findViewById(R.id.txt_address);
        emailText = findViewById(R.id.txt_email);
        usernameText = findViewById(R.id.txt_username);
        passwordText = findViewById(R.id.txt_password);
        allFields = new EditText[]{nameText, addressText, emailText, usernameText, passwordText};
        progressBar = findViewById(R.id.progressBar);
    }
}
