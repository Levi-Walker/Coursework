package edu.andrews.cptr252.leviwalker.activitylifecycletest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LifeCycleActivity extends AppCompatActivity {
    public static final String TAG = "LifeCycleActivity";
    TextView mHelloTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate Called by Student");
        mHelloTextView = findViewById(R.id.helloWorldTextView);
        mHelloTextView.setText("Hi World");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart Called by Student");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause Called by Student");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume Called by Student");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop Called by Student");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy Called by Student");
    }

}