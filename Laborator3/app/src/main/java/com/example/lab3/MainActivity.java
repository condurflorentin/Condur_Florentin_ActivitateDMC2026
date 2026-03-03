package com.example.lab3;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LAB3_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "onCreate - verbose");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart - debug");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume - info");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause - warning");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop - error");
    }


}