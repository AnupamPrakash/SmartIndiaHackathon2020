package com.example.newseye;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    ProgressDialog progressdialog;
    RecyclerView newsList;
    FloatingActionButton fabRefresh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home,container,false);
        newsList = root.findViewById(R.id.newsList);
        newsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        fabRefresh = root.findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayData();
            }
        });
        displayData();
        loadNotification();
        return root;
    }

    private void displayData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, new NetworkHandler().getURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressdialog.dismiss();
//                        Toast.makeText(getActivity(), ""+response.toString(), Toast.LENGTH_SHORT).show();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        News news = gson.fromJson(response, News.class);
//                        Toast.makeText(getActivity(), "Count: "+news.getArticles().size(), Toast.LENGTH_SHORT).show();
                        newsList.setAdapter(new NewsListAdapter(getActivity(),news.getArticles(), 1));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setMessage("Loading...");
        progressdialog.show();
    }
    private void loadNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("NewsEye Alert.")
                        .setContentText("Welcome back!");

        Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
