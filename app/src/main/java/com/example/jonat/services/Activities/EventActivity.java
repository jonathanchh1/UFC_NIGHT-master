package com.example.jonat.services.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jonat.services.R;

public class EventActivity extends AppCompatActivity {

    public static final String Args = "arguments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
