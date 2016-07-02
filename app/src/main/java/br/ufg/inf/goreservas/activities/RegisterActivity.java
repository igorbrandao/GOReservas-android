package br.ufg.inf.goreservas.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import br.ufg.inf.goreservas.R;

public class RegisterActivity extends AppCompatActivity {

    private CreateUserTask mCreateUserTask = null;

    private AutoCompleteTextView mNewUserNameView;
    private AutoCompleteTextView mNewUserEmailView;
    private EditText mNewUserPasswordView;
    private EditText mNewUserRepeatPasswordView;
    private View mRegisterSuccessView;
    private View mRegisterFormView;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        mNewUserNameView = (AutoCompleteTextView) findViewById(R.id.fullName);
        mNewUserEmailView = (AutoCompleteTextView) findViewById(R.id.newUserEmail);
        mNewUserPasswordView = (EditText) findViewById(R.id.newUserPassword);
        mNewUserRepeatPasswordView = (EditText) findViewById(R.id.newUserRepeatedPassword);

        mRegisterSuccessView = findViewById(R.id.register_success);
        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);

        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptAccountCreation();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Button dismissAndLoginButton = (Button) findViewById(R.id.dismiss_and_login);
        dismissAndLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void attemptAccountCreation() throws ExecutionException, InterruptedException {

        // Reset errors.
        mNewUserNameView.setError(null);
        mNewUserEmailView.setError(null);
        mNewUserPasswordView.setError(null);
        mNewUserRepeatPasswordView.setError(null);

        // Store values at the time of the account creation attempt.
        String name = mNewUserNameView.getText().toString();
        String email = mNewUserEmailView.getText().toString();
        String password = mNewUserPasswordView.getText().toString();
        String repeatedPassword = mNewUserRepeatPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //Check for empty name
        if (TextUtils.isEmpty(name)) {
            mNewUserNameView.setError(getString(R.string.error_field_required));
            focusView = mNewUserNameView;
            cancel = true;
        }
        else{
            // Check for a valid name.
            if (!isNameValid(name)) {
                mNewUserNameView.setError(getString(R.string.error_invalid_name));
                focusView = mNewUserNameView;
                cancel = true;
            }
        }
        // Check for empty email address.
        if (TextUtils.isEmpty(email)) {
            mNewUserEmailView.setError(getString(R.string.error_field_required));
            focusView = mNewUserEmailView;
            cancel = true;
        }
        else{
            // Check for a valid email address.
            if (!isEmailValid(email)) {
                mNewUserEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mNewUserEmailView;
                cancel = true;
            }
        }
        // Check for empty password.
        if(TextUtils.isEmpty(password)) {
            mNewUserPasswordView.setError(getString(R.string.error_field_required));
            focusView = mNewUserPasswordView;
            cancel = true;
        }
        else {
            // Check for a valid password.
            if (!isPasswordValid(password)) {
                mNewUserPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mNewUserPasswordView;
                cancel = true;
            }
        }
        // Check for empty repeated password.
        if(TextUtils.isEmpty(repeatedPassword)) {
            mNewUserRepeatPasswordView.setError(getString(R.string.error_field_required));
            focusView = mNewUserRepeatPasswordView;
            cancel = true;
        }
        else {
            // Check for a repeated password match.
            if (!passwordsMatch(password, repeatedPassword)) {
                mNewUserRepeatPasswordView.setError(getString(R.string.error_passwords_mismatch));
                focusView = mNewUserRepeatPasswordView;
                cancel = true;
            }
        }

        if (cancel) {
            // There was an error; don't attempt account creation and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the creation of the user account.
            showProgress(true);
            mCreateUserTask = new CreateUserTask(name, email, password);
            mCreateUserTask.execute((Void) null);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isNameValid(String name) {
        return name.length() > 2;
    }

    private boolean isEmailValid(String email) {
        return Pattern.matches("^(.+)@(.+).com$", email);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean passwordsMatch(String password, String repeatedPassword) {
        return password.equals(repeatedPassword);
    }

    public class CreateUserTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final String mEmail;
        private final String mPassword;

        CreateUserTask(String name, String email, String password) {
            mName = name;
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // TODO: contact application server to persist data.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            return new Random().nextBoolean();
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.register_activity_content);
            mCreateUserTask = null;
            showProgress(false);

            if (success) {
                mRegisterFormView.setVisibility(View.GONE);
                mRegisterSuccessView.setVisibility(View.VISIBLE);
            } else {
                if(new Random().nextBoolean()) {
                    mNewUserEmailView.setError(getString(R.string.error_email_taken));
                    mNewUserEmailView.requestFocus();
                }
                else{
                    viewGroup.addView(View.inflate(RegisterActivity.this, R.layout.fragment_error, null));
                }
            }
        }

        @Override
        protected void onCancelled() {
            mCreateUserTask = null;
            showProgress(false);
        }
    }

}
