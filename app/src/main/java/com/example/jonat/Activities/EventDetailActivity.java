package com.example.jonat.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jonat.services.R;

public class EventDetailActivity extends AppCompatActivity {

    public static final String Args = "arguments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCodntentView(R.layout.activity_detail);
    }
}
