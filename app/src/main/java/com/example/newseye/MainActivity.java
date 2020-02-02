package com.example.newseye;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.graphics.Bitmap;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    Toolbar toolbar;
    User userAccount;
    BottomNavigationView bottomBar;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        Log.d("check","MainActivity");
        if(currentUser==null)
        {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
        loadFragment(new HomeFragment());
        toolbar = findViewById(R.id.main_title);
        bottomBar = findViewById(R.id.bottomNavigation);
        bottomBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            scheduleJob();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void scheduleJob() {
        JobScheduler jobScheduler=(JobScheduler)getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName=new ComponentName(this,NewsUpdateService.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
        .setPeriodic(15 * 60 * 1000, 5 * 60 *1000)
         //       .setOverrideDeadline(30000)
                .setRequiresCharging(true)
                .setRequiredNetworkType(
                        JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true).build();
        int resultcode=jobScheduler.schedule(jobInfo);
        if(resultcode==JobScheduler.RESULT_SUCCESS)
            Log.d("called",""+jobInfo.toString());
        else{
            Log.d("Called","failure");
        }
    }


    private void loadUser(FirebaseUser currentUser) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                progressdialog.dismiss();
//                  Toast.makeText(getActivity(), ""+dataSnapshot.child("username").getValue(), Toast.LENGTH_SHORT).show();
                String name = (String) dataSnapshot.child("username").getValue();
                String userDP = (String) dataSnapshot.child("userDp").getValue();
                String userdep = (String) dataSnapshot.child("department").getValue();
                long artReads = (long) dataSnapshot.child("articlesRead").getValue();
                long artShared = (long) dataSnapshot.child("articlesShared").getValue();
                long favorites = (long) dataSnapshot.child("favorites").getValue();
                userAccount = new User(name,userDP,userdep,artReads,artShared,favorites);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.homeIcon:
                    toolbar.setTitle("Home");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.favorite:
                    toolbar.setTitle("Favorites");
                    fragment = new RecentFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.account:
                    toolbar.setTitle("Account");
                    fragment = new AccountFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
