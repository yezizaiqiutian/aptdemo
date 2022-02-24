package com.gh.aptdemo.hy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gh.aptdemo.R;

/**
 * @author: gh
 * @description:
 * @date: 2022/1/21.
 * @from:
 */

@ContentView(value = R.layout.activity_main)
public class TestActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_hello)
    private TextView tv_hello;
    @ViewInject(R.id.tv_hello2)
    private TextView tv_hello2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewInjectUtils.inject(this);

//        findViewById(R.id.tv_hello).setOnClickListener(view -> {
//            Log.d("ggg", "textviewid:" + tv_hello);
//        });

    }

    @OnClick(R.id.tv_hello)
    public void aaaaa(View view) {
        switch (view.getId()) {
            case R.id.tv_hello:
                Log.d("ggg", "textviewid哈哈哈哈哈哈哈:" + tv_hello);
            break;
        }
    }
}
