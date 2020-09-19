package com.example.newseye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView login_here;
    TextInputEditText register_name,register_dep,register_email,register_password,register_confirm_password;
    MaterialCardView registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        login_here = findViewById(R.id.login_here);
        login_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
        register_name = findViewById(R.id.register_name);
        register_dep = findViewById(R.id.register_dep);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_confirm_password = findViewById(R.id.register_confirm_password);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,department,email,password,confirm_password;
                name = register_name.getText().toString();
                department = register_dep.getText().toString();
                email = register_email.getText().toString();
                password = register_password.getText().toString();
                confirm_password = register_confirm_password.getText().toString();
                if(password.equals(confirm_password))
                    registerUser(name,department,email,password,confirm_password);
            }
        });
    }

    private void registerUser(final String name, final String department, String email, String password, String confirm_password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Successfully registered ", Toast.LENGTH_SHORT).show();
                            dbUserEntry(name,department,user);
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed."+task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void dbUserEntry(String name, String department, FirebaseUser user) {
         DatabaseReference mDatabase;
         mDatabase = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
         mDatabase.child("username").setValue(name);
         mDatabase.child("department").setValue(department);
         mDatabase.child("userDp").setValue("Default");
         mDatabase.child("articlesRead").setValue(0);
         mDatabase.child("articlesShared").setValue(0);
         mDatabase.child("favorites").setValue(0);
         DatabaseReference depDatabase = FirebaseDatabase.getInstance().getReference().child("department").child(department);
         depDatabase.child(user.getUid()).setValue(user.getUid());
//        depDatabase.push().setValue(user.getUid());
    }
}
