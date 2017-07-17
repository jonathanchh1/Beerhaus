package com.example.jonat.beerhaus;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    public static final String ARG_BEERS = "arguments";
    private FragmentManager fragmentManager = getFragmentManager();
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(mToolbar);
        if(savedInstanceState == null){
            fragmentManager.beginTransaction()
                    .add(R.id.beer_detail_container, new BeerDetailFragment())
                    .commit();
        }
    }
}
