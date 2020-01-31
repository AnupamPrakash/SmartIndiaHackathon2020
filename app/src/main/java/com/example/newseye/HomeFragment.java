package com.example.newseye;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    TextView textView;
    ProgressDialog progressdialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home,container,false);
        textView = root.findViewById(R.id.text);
        displayData();
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
                        textView.setText("Count"+response.toString());
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

}
