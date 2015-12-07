package com.andrewsosa.beacon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.flipboard.bottomsheet.BottomSheetLayout;

public class GroupActivity extends AppCompatActivity {

    BottomSheetLayout bottomSheet;

    RecyclerView mRecyclerview;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //bottomSheet = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        //bottomSheet.setShouldDimContentView(false);
        //bottomSheet.showWithSheetView(getLayoutInflater().inflate(R.layout.content_group, bottomSheet, false));

        //float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, getResources().getDisplayMetrics());
        //bottomSheet.setPeekSheetTranslation(px);



        //mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        //mRecyclerview.setLayoutManager(mLayoutManager);
    }

}
