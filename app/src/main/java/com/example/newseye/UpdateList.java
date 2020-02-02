package com.example.newseye;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.TaskExecutor;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.Executor;

public class UpdateList extends AsyncTask<JobParameters,Void,JobParameters> {
    private final JobService jobService;
    private final Context context;
    public UpdateList(JobService jobService,Context context){
        this.jobService=jobService;
        this.context=context;
    }
    @Override
    protected JobParameters doInBackground(JobParameters... jobParameters) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, new NetworkHandler().getURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //progressdialog.dismiss();
//                        Toast.makeText(getActivity(), ""+response.toString(), Toast.LENGTH_SHORT).show();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        News news = gson.fromJson(response, News.class);
                        Log.d("called",news.getArticles().get(0).getTitle());

//                        Toast.makeText(getActivity(), "Count: "+news.getArticles().size(), Toast.LENGTH_SHORT).show();
                       // newsList.setAdapter(new NewsListAdapter(getActivity(),news));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("called",""+error.toString());
                       // Toast.makeText(getActivity(), ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        Log.d("called","calling yo");
        return null;
    }

    @Override
    protected void onPostExecute(JobParameters jobParameters) {
        super.onPostExecute(jobParameters);
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.drawable.logo) //set icon for notification
//                        .setContentTitle("Notifications Example") //set title of notification
//                        .setContentText("This is a notification message");//this is notification message
//
//        Intent notificationIntent = new Intent(context,MainActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());

        Log.d("called","caller");
    }
}