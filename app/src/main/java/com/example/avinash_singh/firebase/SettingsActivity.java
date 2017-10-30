package com.example.avinash_singh.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference userDatabase;
    private FirebaseUser currentUser;
    private TextView displayName,displayStatus;
    private CircleImageView profilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        displayName = findViewById(R.id.display_name);
        displayStatus = findViewById(R.id.display_status);
        profilePic = findViewById(R.id.profile_image);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = currentUser.getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid);

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name         =dataSnapshot.child("name").getValue().toString();
                String status       =dataSnapshot.child("status").getValue().toString();
                String image        =dataSnapshot.child("image").getValue().toString();
                String thumb_image  =dataSnapshot.child("thumbnail").getValue().toString();


                displayName.setText(name);
                displayStatus.setText(status);
                

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
