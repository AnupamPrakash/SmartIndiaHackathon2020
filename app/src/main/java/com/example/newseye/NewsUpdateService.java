package com.example.newseye;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class NewsUpdateService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
      new UpdateList(this,getApplicationContext()).execute(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
