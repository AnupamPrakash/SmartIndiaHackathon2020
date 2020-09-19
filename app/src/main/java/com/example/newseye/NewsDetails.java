package com.example.newseye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewsDetails extends AppCompatActivity {

    TextView newsContent;
    ImageView newsDp;
    int count;
    Toolbar toolbar;
    ImageView favorite,share,link;
//    User userAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        final Article article = (Article) getIntent().getSerializableExtra("Article");
        count = (int) getIntent().getIntExtra("Count",1);
        newsContent = findViewById(R.id.textcard);
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        newsDp = findViewById(R.id.toolbar_image);
        toolbar = findViewById(R.id.AppBar);
        favorite = findViewById(R.id.mark_favorite);
        share = findViewById(R.id.share);
        link = findViewById(R.id.openlink);
        Glide.with(newsDp.getContext()).load(article.getUrlToImage()).into(newsDp);
        newsContent.setText(article.getContent());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long currFav = (long) dataSnapshot.child("articlesRead").getValue();
                        currFav+=1;
                        dbref.child("articlesRead").setValue(currFav);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                dbref.addListenerForSingleValueEvent(valueEventListener);
                Intent intent = new Intent("android.intent.action.VIEW",
                        Uri.parse(article.getUrl()));
                startActivity(intent);
            }
        });
        if(count==1) {
            favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("favorites").child(currentUser.getUid());
                    mDatabase.push().setValue(article);
                    favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long currFav = (long) dataSnapshot.child("favorites").getValue();
                            currFav+=1;
                            dbref.child("favorites").setValue(currFav);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    dbref.addListenerForSingleValueEvent(valueEventListener);
                }
            });
        }
        else if(count==2)
        {
            favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Checkout this latest news update :"+article.getUrl()+"/");
                startActivity(Intent.createChooser(intent,"Share Event via"));
//                startActivity(new Intent(NewsDetails.this,ShareActivity.class));
                final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long currFav = (long) dataSnapshot.child("articlesShared").getValue();
                        currFav+=1;
                        dbref.child("articlesShared").setValue(currFav);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                dbref.addListenerForSingleValueEvent(valueEventListener);
            }
        });
    }
}
