package com.example.jonat.beerhaus;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private TextView mTitle;
    private BeerFragment beerFragment;
    private DrawerLayout mDrawlayout;
    private FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText(mToolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if(savedInstanceState == null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            beerFragment = new BeerFragment();
            fragmentTransaction.add(R.id.fragment_container, beerFragment);
            fragmentTransaction.commit();
        }else{
            beerFragment = (BeerFragment) getFragmentManager().findFragmentById(R.id.fragment_container);
        }

        //Create navigation drawer and inflate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawlayout = (DrawerLayout) findViewById(R.id.drawer);
        //Adding menu icon to Toolbar;
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu_black_24dp, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(), R.color.text_color_detail, getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Set item in checked state
                int id = item.getItemId();
                switch (id) {
                    case R.id.profile:
                        return true;

                    case R.id.invite_menu:
                        return true;

                    case R.id.setting:
                        return true;

                    case R.id.logout:

                        /**
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        item.setChecked(true);
                         **/
                        break;
                }
                //Closing drawer on ItemClick
                mDrawlayout.closeDrawers();
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainact, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            mDrawlayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}