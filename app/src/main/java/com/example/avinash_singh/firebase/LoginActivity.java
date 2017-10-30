package com.example.avinash_singh.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputLayout email,pass;
    private Button login;
    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Toolbar functionality.
        toolbar = findViewById(R.id.tool_bar_login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Toolbar

        progressDialog = new ProgressDialog(this);

        email = findViewById(R.id.email_login);
        pass = findViewById(R.id.pass_login);
        login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String loginEmail = email.getEditText().getText().toString().trim();
                String loginPass = pass.getEditText().getText().toString().trim();

                if(!TextUtils.isEmpty(loginEmail) || !TextUtils.isEmpty(loginPass))
                {
                    progressDialog.setTitle("Logging In !");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("Please wait while check your credentials");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    signInUser(loginEmail,loginPass);
                }
            }
        });




    }

    private void signInUser(String loginEmail, String loginPass)
    {
        mAuth.signInWithEmailAndPassword( loginEmail,loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Intent act= new Intent(LoginActivity.this,MainActivity.class);
                    act.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(act);
                    finish();

                }

                else
                {
                    progressDialog.hide();
                    Toast.makeText(LoginActivity.this,"Credentials provided are wrong! Please check.",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
