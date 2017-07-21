package com.example.jonat.beerhaus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jonat.beerhaus.data.BeerContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = MainActivity.class.getName();
    private ArrayList<Beeritems> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    public final String Product = "products";
    private ProgressBar progressBar;
    private Beeritems items = new Beeritems();
    public final String Favorites = "favorite";
    private String SortOrder = Product;
    private final String BEER = "beer";
    @Bind(R.id.toolbar1)
    Toolbar mToolbar;

    private static final int CURSOR_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbar.setTitle(R.string.beer_haus);
        setSupportActionBar(mToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //Create a grid layout with two columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        //setup span size expanding.
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });

        mRecyclerView.setLayoutManager(layoutManager);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        if(savedInstanceState != null) {
            if (savedInstanceState.containsKey(BEER)) {
                feedsList = savedInstanceState.getParcelableArrayList(BEER);
                adapter.setData(feedsList);
            }

        }

        SortRange(SortOrder);
    }



    private void SortRange(String Choice) {
        if(!Choice.contentEquals(Favorites)) {
            if(isNetworkAvailable(getApplicationContext())) {
                new DownloadTask().execute(Choice);
            }
        } else {
            getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
                }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        switch (SortOrder) {
            case Product:
                menu.findItem(R.id.alcohol_menu).setChecked(true);
                break;
            case Favorites:
                menu.findItem(R.id.action_favorite).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.alcohol_menu:
                if (SortOrder.equals(Favorites)) {
                    getSupportLoaderManager().destroyLoader(CURSOR_LOADER_ID);
                }
                SortOrder = Product;
                SortRange(SortOrder);
                item.setChecked(true);
                break;

            case R.id.action_favorite:
                if(SortOrder.equals(Product)){
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                }
                SortOrder = Favorites;
                SortRange(SortOrder);
                item.setChecked(true);
                break;

            default:
                break;
        }
                return super.onOptionsItemSelected(item);
        }





    private boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if(activeNetworkInfo == null){
            Toast.makeText(getApplicationContext(), "there's no network connection", Toast.LENGTH_SHORT).show();
        }

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(items != null){
            outState.putParcelableArrayList(BEER, feedsList);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                BeerContract.BeerEntry.CONTENT_URI,
                BeerContract.BeerEntry.BEER_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            ArrayList<Beeritems> results = new ArrayList<>();
            //if we have data in database for Fav. beers.
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Beeritems items = new Beeritems(cursor);
                    results.add(items);
                    Log.d(TAG, "beer " + "saved " + cursor.getCount());

                } while (cursor.moveToNext());
            }

        }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    public class DownloadTask extends AsyncTask<String, Void, Integer> implements MyRecyclerViewAdapter.Callbacks{

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {

                String choice = params[0];
                String api_key = "?access_key=";
                URL url = new URL(Constant.BaseURl
                        + choice
                        + api_key
                        + BuildConfig.BEER_API);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                    response.append(line);
                }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new MyRecyclerViewAdapter(MainActivity.this, feedsList, this);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public void onTaskCompleted(Beeritems items, int position) {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.ARG_BEERS, items);
            startActivity(intent);
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray beers = response.optJSONArray("result");
            feedsList = new ArrayList<>();

            for (int i = 0; i < beers.length(); i++) {
                JSONObject mbeers = beers.optJSONObject(i);
                Beeritems item = new Beeritems();
                item.setId(mbeers.optInt("id"));
                item.setTitle(mbeers.optString("name"));
                item.setTags(mbeers.optString("tags"));
                item.setPrice(mbeers.optString("price_in_cents"));
                item.setOrigin(mbeers.optString("origin"));
                item.setAlcohol_level(mbeers.optString("alcohol_content"));
                item.setProducer(mbeers.optString("producer_name"));
                item.setUpdate(mbeers.optString("updated_at"));
                item.setThumbnail(mbeers.optString("image_thumb_url"));
                item.setVarietal(mbeers.optString("varietal"));
                item.setStyle(mbeers.optString("style"));
                item.setCatagory_tertiary(mbeers.optString("tertiary_category"));
                item.setVolume(mbeers.optString("volume_in_milliliters"));
                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}