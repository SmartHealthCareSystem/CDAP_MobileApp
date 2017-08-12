package edu.smarthealthcare.smarthealthcareapp;

import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.auth.api.signin.internal.SignInHubActivity;

import java.util.HashMap;

import edu.smarthealthcare.smarthealthcareapp.Utils.NetConnect;

public class SignUpActivity extends AppCompatActivity {

    private ActionProcessButton btnSignup;

    private TextInputEditText txtUsername;
    private TextInputEditText txtPassword;
    private TextInputEditText txtUserEmail;
    private TextInputEditText txtConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_register);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSignup = (ActionProcessButton)findViewById(R.id.btnSignUp);
        btnSignup.setMode(ActionProcessButton.Mode.ENDLESS);
        btnSignup.setColorScheme(ContextCompat.getColor(this,R.color.colorPrimary),ContextCompat.getColor(this,R.color.white),ContextCompat.getColor(this,R.color.colorPrimary),ContextCompat.getColor(this,R.color.white));

        btnSignup = (ActionProcessButton) findViewById(R.id.btnSignUp);
        txtUsername = (TextInputEditText) findViewById(R.id.tiet_user_name);
        txtUserEmail = (TextInputEditText) findViewById(R.id.tiet_user_email);
        txtPassword = (TextInputEditText) findViewById(R.id.tiet_user_password);
        txtConfirmPass = (TextInputEditText) findViewById(R.id.tiet_user_conf_password);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String textEmail = txtUserEmail.getText().toString().trim();
                final String textPassword = txtPassword.getText().toString().trim();
                final String textUsername = txtUsername.getText().toString().trim();
                final String textConfirmPassword = txtConfirmPass.getText().toString().trim();

                if (textEmail.length() == 0 || textPassword.length()==0 || textUsername.length() ==0 || textConfirmPassword.length()==0){

                    if (textUsername.length()==0)
                        txtUsername.setError("Please enter your user name");
                    if (textEmail.length()==0)
                        txtUserEmail.setError("Please enter your email address");
                    if (textPassword.length()==0)
                        txtPassword.setError("Please enter your password");
                    if (textConfirmPassword.length()==0)
                        txtConfirmPass.setError("Please enter your confirm password");

                }

                else if(!isValidEmail(textEmail)){
                    txtUserEmail.setError("Please enter a valid email address");
                }
                else if(!isMatching(textPassword, textConfirmPassword)){
                    txtConfirmPass.setError("Please enter a matching password");
                }

                else{

                    if (NetConnect.isNetworkConnected(SignUpActivity.this)){
                        btnSignup.setProgress(50);

//                        registerPatient();
                    }
                    else{
                        btnSignup.setProgress(-1); //fail
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() { // set to normal after 2 seconds
                                btnSignup.setProgress(0);
                            }
                        },2000);
                        Toast.makeText(SignUpActivity.this, R.string.error_no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public boolean isMatching(String pass, String cPass){
        if (cPass.equals(pass))
            return true;
        return false;

    }
}
