package com.xadaptersimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/1/12.
 */

public class MultipleItemActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            strings.add(i + "");
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
