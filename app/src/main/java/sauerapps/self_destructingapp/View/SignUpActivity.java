package sauerapps.self_destructingapp.View;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sauerapps.self_destructingapp.R;

public class SignUpActivity extends AppCompatActivity {


    @InjectView(R.id.user_name_input_sign_up_screen) protected EditText mUserName;
    @InjectView(R.id.password_input_field_sign_up_screen) protected EditText mPassword;
    @InjectView(R.id.email_input_field_sign_up_screen) protected EditText mEmail;
    @InjectView(R.id.sign_up_button_sign_up_screen) protected Button mSignUpButton;
    @InjectView(R.id.progressBarSignUpPage) protected ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);

                String username = mUserName.getText().toString();
                String password = mPassword.getText().toString();
                String email = mEmail.getText().toString();

                username = username.trim();
                password = password.trim();
                email = email.trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SignUpActivity.this);
                    alertBuilder.setMessage(R.string.sign_up_error_message)
                            .setTitle(R.string.sign_up_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = alertBuilder.create();
                    dialog.show();
                }
                else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    ParseUser newUser = new ParseUser();

                    newUser.setEmail(email);
                    newUser.setPassword(password);
                    newUser.setUsername(username);

                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SignUpActivity.this);
                                alertBuilder.setMessage(e.getMessage())
                                        .setTitle(R.string.sign_up_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = alertBuilder.create();
                                dialog.show();
                            }
                        }
                    });

                }
            }
        });
    }
}
