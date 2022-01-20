package com.gh.aptdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gh.apt_annotation.BindView;
import com.gh.apt_library.BindViewTools;

public class MainActivity2 extends AppCompatActivity {

    @BindView(R.id.tv_hello)
    TextView mTvHello;
    @BindView(R.id.tv_hello2)
    TextView mTvHello2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindViewTools.bind(this);
        findViewById(R.id.tv_hello).setOnClickListener(view -> {
            Log.d("ggg", "textviewid:" + mTvHello);
        });

    }
}