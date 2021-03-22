package com.example.rdvmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("test", "onCreateEvent");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test", "onResumeEvent");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "onPauseEvent");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("test", "onStopEvent");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("test", "onRestartEvent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "onDestroyEvent");
    }
}