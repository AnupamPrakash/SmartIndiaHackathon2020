package com.example.newseye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShareActivity extends AppCompatActivity {

    String department;
    List<User> userList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        userList = new ArrayList<>();
        loadUsers(currentUser);
        recyclerView = findViewById(R.id.userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Toast.makeText(this, ""+userList.size(), Toast.LENGTH_SHORT).show();
    }

    private void loadUsers(FirebaseUser currentUser) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                department = (String) dataSnapshot.child("department").getValue();
//                if(department!=null)
//                    Toast.makeText(ShareActivity.this, ""+department, Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(ShareActivity.this, "NUll", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
        DatabaseReference depref = FirebaseDatabase.getInstance().getReference().child("users");
        ValueEventListener DepEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    if (user.getDepartment().equals(department)) {
                        userList.add(user);
                    }
                }
                recyclerView.setAdapter(new UserListAdapter(ShareActivity.this,userList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        depref.addListenerForSingleValueEvent(DepEventListener);
    }
}
