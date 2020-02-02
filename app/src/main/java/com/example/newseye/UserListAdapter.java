package com.example.newseye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.DataViewHolder> {
    private final List<User> userList;
    private Context context;

    public UserListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newscard, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        public DataViewHolder(@NonNull View view) {
            super(view);
            textView = view.findViewById(R.id.textcard);
            imageView = view.findViewById(R.id.newsDp);
        }
    }
}
