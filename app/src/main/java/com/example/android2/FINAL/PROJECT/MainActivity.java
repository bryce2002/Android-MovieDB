package com.example.android2.FINAL.PROJECT;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android2.FINAL.PROJECT.fragment.OverviewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list, new OverviewFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
