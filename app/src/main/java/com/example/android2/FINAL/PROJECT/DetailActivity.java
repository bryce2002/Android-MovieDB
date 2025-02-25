package com.example.android2.FINAL.PROJECT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android2.FINAL.PROJECT.fragment.DetailFragment;
import com.example.android2.FINAL.PROJECT.model.MovieData;

public class DetailActivity extends AppCompatActivity {

    public static void start(Context context, MovieData movieData) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.class.getSimpleName(), movieData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MovieData movieData = getIntent().getParcelableExtra(DetailActivity.class.getSimpleName());

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list, DetailFragment.newInstance(movieData))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idNumber = item.getItemId();
        if(idNumber == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
