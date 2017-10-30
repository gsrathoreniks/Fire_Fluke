package com.example.avinash_singh.firebase;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private android.support.v7.widget.Toolbar mtoolbar;
    private ViewPager viewPager;
    private SectionPageAdapter sectionPageAdapter;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mtoolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Fire Fluke");


        viewPager = findViewById(R.id.viewPagerMain);
        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(sectionPageAdapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }



    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            sendToStart();
        }


    }

    private void sendToStart()
    {

        startActivity(new Intent(MainActivity.this,StartActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

         getMenuInflater().inflate(R.menu.main_menu,menu);


         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);

         if (item.getItemId() == R.id.mainLog_out)
         {
                FirebaseAuth.getInstance().signOut();
                sendToStart();
         }

         else if (item.getItemId() == R.id.account_settings)
         {
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
         }



         return  true;
    }
}
