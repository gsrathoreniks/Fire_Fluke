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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register_Activity extends AppCompatActivity {

    private TextInputLayout name;
    private TextInputLayout email;
    private TextInputLayout pass;
    private Button createAcc;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private ProgressDialog progressBar;
    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        mAuth = FirebaseAuth.getInstance();

        progressBar = new ProgressDialog(this);


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        createAcc = findViewById(R.id.create);

        // Toolbar functionality
        toolbar = findViewById(R.id.tool_bar_register);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String displayName = name.getEditText().getText().toString().trim();
                String displayEmail = email.getEditText().getText().toString().trim();
                String displayPass = pass.getEditText().getText().toString().trim();

                if (!TextUtils.isEmpty(displayName) || !TextUtils.isEmpty(displayEmail) || !TextUtils.isEmpty(displayPass))
                {
                    progressBar.setTitle("Registering User");
                    progressBar.setMessage("Please wait while we create your account");
                    progressBar.setCanceledOnTouchOutside(false);
                    progressBar.show();
                    register_user(displayName,displayEmail,displayPass);
                }

            }

            private void register_user(final String displayName, String displayEmail, String displayPass) {

                mAuth.createUserWithEmailAndPassword(displayEmail,displayPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = currentUser.getUid();

                            database = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String,String> map = new HashMap<>();
                            map.put("name",displayName);
                            map.put("status","Hi there I'm using Fire Fluke.");
                            map.put("image","default");
                            map.put("thumbnail","default");

                            database.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        progressBar.dismiss();
                                        startActivity(new Intent(Register_Activity.this,MainActivity.class));
                                        finish();
                                    }

                                }
                            });


                        }
                        else
                        {
                            progressBar.hide();
                            Toast.makeText(Register_Activity.this,"You got some Error in Connections",Toast.LENGTH_LONG).show();
                        }



                    }
                });

            }
        });
    }
}
