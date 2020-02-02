package com.example.newseye;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecentFragment extends Fragment {
    public RecentFragment() {
    }
    ProgressDialog progressdialog;
    FirebaseUser currentUser;
    RecyclerView recyclerView;
    List<Article> articleList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recent,container,false);
        recyclerView = root.findViewById(R.id.favnewsList);
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        articleList = new ArrayList<>();
        loadRecent(currentUser);
        return root;
    }

    private void loadRecent(FirebaseUser currentUser) {
        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setMessage("Loading...");
        progressdialog.show();
        DatabaseReference depref = FirebaseDatabase.getInstance().getReference().child("favorites").child(currentUser.getUid());
        ValueEventListener DepEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Article article = dataSnapshot1.getValue(Article.class);
//                    Toast.makeText(getActivity(), ""+article.getTitle(), Toast.LENGTH_SHORT).show();
                    articleList.add(article);
                    progressdialog.dismiss();
                }
//                Toast.makeText(getActivity(), ""+articleList.size(), Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(new NewsListAdapter(getActivity(),articleList,2));
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        depref.addListenerForSingleValueEvent(DepEventListener);
    }
}
