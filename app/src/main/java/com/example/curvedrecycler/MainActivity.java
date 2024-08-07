package com.example.curvedrecycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Point;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {




    private YourAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;

        recyclerView.getLayoutParams().height = screenHeight;



        CurvedLayoutRecyclerView layoutManager = new CurvedLayoutRecyclerView(getResources(),screenHeight);





        recyclerView.setLayoutManager(layoutManager);

        ArrayList<YourAdapter.AppItem> appList = new ArrayList<>();
        appList.add(new YourAdapter.AppItem(R.drawable.icons8_app_50, "One"));
        appList.add(new YourAdapter.AppItem(R.drawable.icons8_app_50__1_, "Two"));
        appList.add(new YourAdapter.AppItem(R.drawable.icons8_app_50__2_, "Three"));
        appList.add(new YourAdapter.AppItem(R.drawable.icons8_app_50__3_, "Four"));
        appList.add(new YourAdapter.AppItem(R.drawable.icons8_app_50__4_, "Five"));
        adapter = new YourAdapter(this, appList);



        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}