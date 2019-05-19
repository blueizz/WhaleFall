package com.blueizz.thread;

import android.os.AsyncTask;
import android.util.Log;

public class TestAsyncTask extends AsyncTask<String, Integer, String> {
    private static final String TAG = "TestAsyncTask";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG, "onPreExecute:" + Thread.currentThread().getName());
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.i(TAG, "doInBackground:" + Thread.currentThread().getName());
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.i(TAG, "onProgressUpdate:" + Thread.currentThread().getName());
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i(TAG, "onPostExecute:" + Thread.currentThread().getName());
    }
}
