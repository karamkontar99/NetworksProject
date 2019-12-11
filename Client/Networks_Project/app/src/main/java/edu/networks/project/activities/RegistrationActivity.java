package edu.networks.project.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import edu.networks.project.MyApplication;
import edu.networks.project.R;
import edu.networks.project.messages.RegistrationRequest;
import edu.networks.project.messages.RegistrationResponse;
import edu.networks.project.services.RegistrationService;

public class RegistrationActivity extends AppCompatActivity {

    @Inject
    RegistrationService registrationService;
    private AutoCompleteTextView nameText, addressText, emailText, usernameText, passwordText, allFields[];
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ((MyApplication) getApplicationContext()).getApplicationComponent().inject(this);
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
            for (AutoCompleteTextView field : allFields)
                field.setEnabled(true);
        }

        @Override
        protected RegistrationResponse doInBackground(RegistrationRequest... requests) {
            RegistrationRequest request = requests[0];
            RegistrationResponse response = registrationService.execute(request);
            return response;
        }

        @Override
        protected void onPostExecute(RegistrationResponse response) {
            super.onPostExecute(response);
            progressBar.setVisibility(View.GONE);
            for (AutoCompleteTextView field : allFields)
                field.setEnabled(true);
            if (response.error != null) {
                new AlertDialog
                        .Builder(getApplicationContext())
                        .setTitle("Error")
                        .setMessage(response.error)
                        .setPositiveButton("OK", null)
                        .show();
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
        boolean valid = true;
        for (AutoCompleteTextView field : allFields) {
            if (field.getText().toString().isEmpty()) {
                field.setError("field cannot be empty");
                valid = false;
            } else
                field.setError("");
        }
        return valid;
    }

    private void linkUI() {
        nameText = findViewById(R.id.txt_name);
        addressText = findViewById(R.id.txt_address);
        emailText = findViewById(R.id.txt_email);
        usernameText = findViewById(R.id.txt_username);
        passwordText = findViewById(R.id.txt_password);
        allFields = new AutoCompleteTextView[]{nameText, addressText, emailText, usernameText, passwordText};
        progressBar = findViewById(R.id.progressBar);
    }
}
