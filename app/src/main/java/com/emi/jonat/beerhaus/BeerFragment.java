package com.emi.jonat.beerhaus;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.emi.jonat.beerhaus.data.BeerContract;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_BACKDROP_THUMBNAIL;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_BEER_ID;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_CATEGORY;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_CONTENT;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_DESCRIPTION;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_IMAGE_THUMBNAIL;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_NAME;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_ORIGIN;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_PRICE;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_PRODUCE;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_STYLE;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_UPDATED;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_VARIETAL;
import static com.emi.jonat.beerhaus.data.BeerContract.BeerEntry.COLUMN_VOLUME;

/**
 * Created by jonat on 12/26/2017.
 */

public class BeerFragment extends Fragment {
    public static final String LOG_TAG = BeerFragment.class.getSimpleName();
    private ArrayList<Beeritems> feedsList;
    private RecyclerView mRecyclerView;
    public final String SORT_KEY = "choice";
    public final String BeerKey = "beer";
    private ProgressBar progressBar;
    public final String FAVORITE  = "favorite";
    private SwipeRefreshLayout swipeContainer;
    private TextView EmptyState;
    Button btnRetry;
    private BeerAdapter.Callbacks mCallbacks;
    private static final String[] BEER_COLUMNS = {
            COLUMN_BEER_ID,
            COLUMN_NAME,
            COLUMN_CONTENT,
            COLUMN_DESCRIPTION,
            COLUMN_PRICE,
            COLUMN_ORIGIN,
            COLUMN_PRODUCE,
            COLUMN_UPDATED,
            COLUMN_IMAGE_THUMBNAIL,
            COLUMN_VARIETAL,
            COLUMN_STYLE,
            COLUMN_CATEGORY,
            COLUMN_VOLUME,
            COLUMN_BACKDROP_THUMBNAIL
    };
    private final String PRODUCTS = "products";
    private String SortOrder = PRODUCTS;
    private ApiInterface apiService;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 60;
    private int currentPage = PAGE_START;
    private BeerAdapter mAdapter;





    public BeerFragment(){
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.beer_container, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_list);
        //Create a grid layout with two columns
        btnRetry = (Button) rootView.findViewById(R.id.error_btn_retry);
        EmptyState = rootView.findViewById(R.id.empty_state);


        feedsList = new ArrayList<>();
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);



        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        /**
        //setup span size expanding.
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });

         **/

        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        mRecyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                if(!SortOrder.equals(FAVORITE)) {
                    isLoading = true;
                    currentPage += 1;
                    loadNextPage(SortOrder);
                }
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);

        if(savedInstanceState != null) {
            if (savedInstanceState.containsKey(SORT_KEY)) {
                SortOrder = savedInstanceState.getString(SORT_KEY);
            }

            if (savedInstanceState.containsKey(BeerKey)) {
                feedsList = savedInstanceState.getParcelableArrayList(BeerKey);
            }else {
                SortRange(SortOrder);
            }

        }
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // once the network request has completed successfully.
                FetchBeerRefresh(SortOrder);
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortRange(SortOrder);
            }
        });
        mCallbacks();
        SortRange(SortOrder);
       return rootView;
    }






    private void loadNextPage(String sortOrder) {
        Log.d(LOG_TAG, "loadNextPage: " + currentPage);
        Call<BeerResponse> call = apiService.getBeerPages(sortOrder, currentPage, BuildConfig.BEER_API);
        call.enqueue(new Callback<BeerResponse>() {

            @Override
            public void onResponse(Call<BeerResponse> call, final Response<BeerResponse> response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            mAdapter.removeLoadingFooter();
                            isLoading = false;
                            Log.wtf(LOG_TAG, "response : " + response.body().getResults());
                            ArrayList<Beeritems> results = response.body().getResults();
                            mAdapter.addAll(results);
                            mAdapter.notifyDataSetChanged();
                            if (currentPage != TOTAL_PAGES) mAdapter.addLoadingFooter();
                            else isLastPage = true;
                            progressBar.setVisibility(View.GONE);
                            btnRetry.setVisibility(View.GONE);
                        }
                    }
                }).run();
            }

            @Override
            public void onFailure(Call<BeerResponse> call, Throwable t) {
                // handle error
                t.printStackTrace();
                progressBar.setVisibility(View.VISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
                mAdapter.showRetry(true, fetchErrorMessage(t));
            }

            private String fetchErrorMessage(Throwable t) {

                String errorMsg = getResources().getString(R.string.error_msg_unknown);
                if (!isNetworkAvailable(getActivity())) {
                    errorMsg = getResources().getString(R.string.error_msg_no_internet);
                } else if (t instanceof TimeoutException) {
                    errorMsg = getResources().getString(R.string.error_msg_timeout);
                }
                return errorMsg;
            }

        });
    }

    private void FetchBeerRefresh(String sortOrder) {
        mAdapter.clear();
        // ...the data has come back, add new items to your adapter...
        mAdapter.addAll(feedsList);
        fetchBeers(sortOrder);
        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false);
    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        switch (SortOrder) {
            case PRODUCTS:
                menu.findItem(R.id.alcohol_menu).setChecked(true);
                break;
            case FAVORITE:
                menu.findItem(R.id.action_favorite).setChecked(true);
                break;
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (isNetworkAvailable(getActivity())) {
                    mAdapter.getFilter().filter(query);
                } else {
                    EmptyState.setVisibility(View.VISIBLE);
                }
                return true;
        }
            @Override
            public boolean onQueryTextChange(String newText){
            if (isNetworkAvailable(getActivity())) {
              mAdapter.getFilter().filter(newText);
            } else {
                EmptyState.setVisibility(View.VISIBLE);
            }
                return true;
    }
});

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.alcohol_menu:
                SortOrder = PRODUCTS;
                SortRange(SortOrder);
                item.setChecked(true);
                break;

            case R.id.action_favorite:
                SortOrder = FAVORITE;
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
            Toast.makeText(getActivity(), "there's no network connection", Toast.LENGTH_SHORT).show();
        }

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!SortOrder.contentEquals(FAVORITE)) {
            outState.putString(SORT_KEY, SortOrder);
        }
        if (feedsList != null) {
            outState.putParcelableArrayList(BeerKey, feedsList);
        }
        super.onSaveInstanceState(outState);
    }


    private void fetchBeers(String sortOrder) {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final Call<BeerResponse> call = apiService.getBeerPages(sortOrder, currentPage, BuildConfig.BEER_API);
        call.enqueue(new Callback<BeerResponse>() {
            @Override
            public void onResponse(Call<BeerResponse> call, final Response<BeerResponse> response) {
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         int statusCode = response.code();
                         if (response.isSuccessful()) {
                             ArrayList<Beeritems> beers = response.body().getResults();
                             if(beers != null) {
                                mAdapter = new BeerAdapter(getActivity(), R.layout.beer_content, beers, mCallbacks);
                                 mRecyclerView.setAdapter(mAdapter);
                                 mAdapter.notifyDataSetChanged();
                                 if (currentPage <= TOTAL_PAGES) mAdapter.addLoadingFooter();
                                 else isLastPage = true;
                                 progressBar.setVisibility(View.GONE);
                                 btnRetry.setVisibility(View.GONE);
                                 EmptyState.setVisibility(View.GONE);
                             }
                         }
                     }
                 }).run();
            }

            @Override
            public void onFailure(Call<BeerResponse> call, Throwable t) {
                // Log error here since request failed
                Log.d(LOG_TAG, "throwable" + t.toString());
                progressBar.setVisibility(View.VISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
                EmptyState.setVisibility(View.VISIBLE);
            }
        });



    }



    private void mCallbacks() {
        mCallbacks = new BeerAdapter.Callbacks() {
            @Override public void onTaskCompleted(Beeritems items, int position) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(DetailActivity.ARG_BEERS, items);
            startActivity(intent);
            }
        };
    }

    private void SortRange(String choice) {
        if(!choice.equals(FAVORITE)) {
            if(isNetworkAvailable(getActivity())) {
                fetchBeers(choice);
                EmptyState.setVisibility(View.GONE);
               btnRetry.setVisibility(View.GONE);
            }else {
                EmptyState.setVisibility(View.VISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
            }
        } else {
            if(isNetworkAvailable(getActivity())) {
                new FetchFav(getActivity()).execute();
                EmptyState.setVisibility(View.GONE);
                btnRetry.setVisibility(View.GONE);
            }else {
                EmptyState.setVisibility(View.VISIBLE);
                btnRetry.setVisibility(View.VISIBLE);

            }
        }
    }


    public class FetchFav extends AsyncTask<String, Void, ArrayList<Beeritems>> {

        private Context mContext;

        //constructor
        public FetchFav(Context context) {
            mContext = context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Beeritems> doInBackground(String... params) {
            Cursor cursor = mContext.getContentResolver().query(
                    BeerContract.BeerEntry.CONTENT_URI,
                    BEER_COLUMNS,
                    null,
                    null,
                    null
            );

            return getFavMoviesFromCursor(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Beeritems> beeritems) {
            //we got Fav movies so let's show them
            if (beeritems != null) {
                if (mAdapter != null) {
                    mAdapter.setData(beeritems);
                }
                feedsList = new ArrayList<>();
                feedsList.addAll(beeritems);

                Log.d(LOG_TAG, "Favorites :" + feedsList.toString());
            } else {
                Log.d(LOG_TAG, getString(R.string.nofav));
            }
        }

        private ArrayList<Beeritems> getFavMoviesFromCursor(Cursor cursor) {
            ArrayList<Beeritems> results = new ArrayList<>();
            //if we have data in database for Fav. movies.
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Beeritems beer = new Beeritems(cursor);
                    results.add(beer);
                } while (cursor.moveToNext());
                cursor.close();
            }
            return results;
        }
    }


}
