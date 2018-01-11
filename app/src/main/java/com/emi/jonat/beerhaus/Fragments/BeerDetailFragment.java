package com.emi.jonat.beerhaus.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emi.jonat.beerhaus.Activities.DetailActivity;
import com.emi.jonat.beerhaus.Models.Beeritems;
import com.emi.jonat.beerhaus.Favorited;
import com.emi.jonat.beerhaus.Services.FetchStoresTask;
import com.emi.jonat.beerhaus.R;
import com.emi.jonat.beerhaus.Models.Store;
import com.emi.jonat.beerhaus.Activities.StoreActivity;
import com.emi.jonat.beerhaus.Adapters.StoreAdapter;
import com.emi.jonat.beerhaus.data.BeerContract;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jonat on 6/26/2017.
 */

public class BeerDetailFragment extends Fragment implements FetchStoresTask.Listener, StoreAdapter.Callbacks {

    public static final String LOG_TAG = BeerDetailFragment.class.getSimpleName();
    private Beeritems items = new Beeritems();
    View rootView;
    LayoutInflater mLayoutInflater;
    ShareActionProvider mShareActionProvider;

    @BindView(R.id.name_title)
    TextView mTitle;
    @BindView(R.id.alcohol_content)
    TextView mContent;
    @BindView(R.id.volume)
    TextView volume;
    @BindView(R.id.category)
    TextView category;
    @BindView(R.id.tags)
    TextView description;
    @BindView(R.id.updated_at)
    TextView update;

    @BindView(R.id.origin)
    TextView origin;
    @BindView(R.id.price)
    TextView prices;
    @BindView(R.id.producer)
    TextView producer;
    @BindView(R.id.style)
    TextView style;
    @BindView(R.id.varietal)
    TextView varietal;
    @BindView(R.id.detail_title)
    TextView mName;
    @BindView(R.id.name)
    TextView mdetail_name;
    @BindView(R.id.detail_alcohol_content)
    TextView mdetail_alcohol;
    @BindView(R.id.detail_category_textview)
    TextView mdetail_category;
    @BindView(R.id.detail_description_textview)
    TextView mdetail_description;
    @BindView(R.id.detail_update_textview)
    TextView mdetail_update;
    @BindView(R.id.detail_origin_textview)
    TextView mdetail_origin;
    @BindView(R.id.detail_price_textview)
    TextView mdetail_price;
    @BindView(R.id.detail_producer_textview)
    TextView mdetail_producer;
    @BindView(R.id.detail_style_textview)
    TextView mdetail_style;
    @BindView(R.id.detail_varietal_textview)
    TextView mdetail_varietal;
    @BindView(R.id.detail_volume_textview)
    TextView mdetail_volume;
    @BindView(R.id.store_list)
    RecyclerView mRecyclerViewStore;
    public static final String EXTRA_STORE = "EXTRA_STORES";
    private StoreAdapter mStoreListAdapter;
    Toast mToast;
    private DatabaseReference mDatabaseReference;
    public BeerDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout)
                activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null && activity instanceof DetailActivity) {
            appBarLayout.setTitleEnabled(false);
        }

        ImageView backdrop = ((ImageView) activity.findViewById(R.id.background_image));
        if (backdrop != null) {
            String mbackdrop = items.getBackdropImage();
            Picasso.with(getActivity()).load(mbackdrop).into(backdrop);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Store> stores = mStoreListAdapter.getmStores();
        if (stores != null && !stores.isEmpty()) {
            outState.putParcelableArrayList(EXTRA_STORE, stores);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mLayoutInflater = inflater;

        Bundle arguments = getArguments();
        Intent intent = getActivity().getIntent();

        if (arguments != null || intent != null && intent.hasExtra(DetailActivity.ARG_BEERS)) {

            rootView = mLayoutInflater.inflate(R.layout.detail_contents, container, false);
            if (arguments != null) {
                items = getArguments().getParcelable(DetailActivity.ARG_BEERS);
            } else {
                items = intent.getParcelableExtra(DetailActivity.ARG_BEERS);
            }


            mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            ButterKnife.bind(this, rootView);
            mName.setText(items.getTitle());
            getViews(items, rootView);

            //For horizontal list of trailers
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewStore.setLayoutManager(layoutManager);
            mStoreListAdapter = new StoreAdapter(new ArrayList<Store>(), this);
            mRecyclerViewStore.setAdapter(mStoreListAdapter);
            mRecyclerViewStore.setNestedScrollingEnabled(false);

            if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_STORE)) {
                ArrayList<Store> stores = savedInstanceState.getParcelableArrayList(EXTRA_STORE);
                mStoreListAdapter.add(stores);
            } else {
                fetchStores(rootView);
            }
        }
        fetchStores(rootView);

        return rootView;
    }

    private void fetchStores(View view) {
        FetchStoresTask storetask = new FetchStoresTask(this);
        storetask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, items.getId());
    }


    @Override
    public void onFetchFinished(ArrayList<Store> stores) {
       if(stores != null){
           mStoreListAdapter.clear();
           mStoreListAdapter.add(stores);
           Log.d(LOG_TAG, "results : " + stores.toString());
       }else {
           if (mStoreListAdapter.getItemCount() < 0) {
               Log.d(LOG_TAG, "no arraylist found");
               }
           }
       }


    private String getTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat(" MM/dd/yy h:mm a", Locale.US);
        String dateString = df.format(time);

        return dateString;
    }

    public static String formatDate(String unFormattedTime) {
        String formattedTime;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = sdf.parse(unFormattedTime);
            if (date != null) {
                sdf = new SimpleDateFormat(" MM/dd/yy h:mm a", Locale.US);
                formattedTime = sdf.format(date);
                return formattedTime;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void getViews(Beeritems items, View view) {

        if(items != null) {
            String name = items.getTitle();
            mTitle.setText(name);
            Log.d(LOG_TAG, "title :" + name);
            mdetail_name.setText(getResources().getString(R.string.name));
            mContent.setText(String.valueOf(items.getAlcohol_level()));
            mdetail_alcohol.setText(getResources().getString(R.string.alcohol_content));
            description.setText(items.getTags());
            mdetail_description.setText(getResources().getString(R.string.description));
            style.setText(items.getStyle());
            mdetail_style.setText(getResources().getString(R.string.style));
            category.setText(items.getCatagory_tertiary());
            mdetail_category.setText(getResources().getString(R.string.category));
            update.setText(formatDate(items.getUpdate()));
            mdetail_update.setText(getResources().getString(R.string.update));
            origin.setText(items.getOrigin());
            mdetail_origin.setText(getResources().getString(R.string.origin));
            prices.setText(String.valueOf(items.getPrice()));
            mdetail_price.setText(getResources().getString(R.string.price));
            producer.setText(items.getProducer());
            mdetail_producer.setText(getResources().getString(R.string.producer));
            varietal.setText(items.getVarietal());
            mdetail_varietal.setText(getResources().getString(R.string.varietal));
            volume.setText(String.valueOf(items.getVolume()));
            mdetail_volume.setText(getResources().getString(R.string.volume));
        }
        Log.d(LOG_TAG, "beer detail: " + items.getUpdate() + items.getProducer() + items.getCatagory_tertiary() + items.getTags());

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (items != null) {

            inflater.inflate(R.menu.main_detail, menu);
            Log.d(LOG_TAG, "detail Menu created");
            final MenuItem action_fav = menu.findItem(R.id.favorite_icon);
            MenuItem action_share = menu.findItem(R.id.action_share);
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(action_share);

            //set  icon on toolbar for favored movies
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... params) {
                    return Favorited.isBeerFavorited(getActivity(), items.getId());
                }

                @Override
                protected void onPostExecute(Integer isFavored) {
                    action_fav.setIcon(isFavored == 1 ?
                            R.drawable.ic_favorite_black_24dp :
                            R.drawable.ic_favorite_border_black_24dp);
                }
            }.execute();

        }
    }

    @SuppressLint("StaticFieldLeak")
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.favorite_icon:
                if (items != null) {
                    // check if movie is favored or not
                    new AsyncTask<Void, Void, Integer>() {

                        @Override
                        protected Integer doInBackground(Void... params) {
                            return Favorited.isBeerFavorited(getActivity(), items.getId());
                        }

                        @Override
                        protected void onPostExecute(Integer isFavored) {
                            // if it is in favorites
                            if (isFavored == 1) {
                                // delete from favorites
                                new AsyncTask<Void, Void, Integer>() {
                                    @Override
                                    protected Integer doInBackground(Void... params) {
                                        return getActivity().getContentResolver().delete(
                                                BeerContract.BeerEntry.CONTENT_URI,
                                                BeerContract.BeerEntry.COLUMN_BEER_ID + " = ?",
                                                new String[]{Integer.toString(items.getId())}
                                        );
                                    }

                                    @Override
                                    protected void onPostExecute(Integer rowsDeleted) {
                                        item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                                        if (mToast != null) {
                                            mToast.cancel();
                                        }
                                        mToast = Toast.makeText(getActivity(), getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT);
                                        mToast.show();
                                    }
                                }.execute();
                            }
                            // if it is not in favorites
                            else {
                                // add to favorites
                                new AsyncTask<Void, Void, Uri>() {
                                    @Override
                                    protected Uri doInBackground(Void... params) {
                                        ContentValues values = new ContentValues();
                                        if (items != null) {
                                            values.put(BeerContract.BeerEntry.COLUMN_BEER_ID, items.getId());
                                            values.put(BeerContract.BeerEntry.COLUMN_NAME, items.getTitle());
                                            values.put(BeerContract.BeerEntry.COLUMN_CONTENT, items.getAlcohol_level());
                                            values.put(BeerContract.BeerEntry.COLUMN_DESCRIPTION, items.getTags());
                                            values.put(BeerContract.BeerEntry.COLUMN_PRICE, items.getPrice());
                                            values.put(BeerContract.BeerEntry.COLUMN_ORIGIN, items.getOrigin());
                                            values.put(BeerContract.BeerEntry.COLUMN_PRODUCE, items.getProducer());
                                            values.put(BeerContract.BeerEntry.COLUMN_UPDATED, items.getUpdate());
                                            values.put(BeerContract.BeerEntry.COLUMN_IMAGE_THUMBNAIL, items.getThumbnail());
                                            if(items.getVarietal() != null) {
                                                values.put(BeerContract.BeerEntry.COLUMN_VARIETAL, items.getVarietal());
                                            }else{
                                                items.setVarietal(getString(R.string.unavailabledata));
                                                values.put(BeerContract.BeerEntry.COLUMN_VARIETAL, items.getVarietal());
                                            }
                                            values.put(BeerContract.BeerEntry.COLUMN_STYLE, items.getStyle());
                                            if(items.getCatagory_tertiary() != null) {
                                                values.put(BeerContract.BeerEntry.COLUMN_CATEGORY, items.getCatagory_tertiary());
                                            }else{
                                                items.setCatagory_tertiary(getString(R.string.unavailabledata));
                                                values.put(BeerContract.BeerEntry.COLUMN_CATEGORY, items.getCatagory_tertiary());
                                            }
                                            values.put(BeerContract.BeerEntry.COLUMN_VOLUME, items.getVolume());
                                            values.put(BeerContract.BeerEntry.COLUMN_BACKDROP_THUMBNAIL, items.getBackdropImage());

                                        }

                                            return getActivity().getContentResolver().insert(BeerContract.BeerEntry.CONTENT_URI, values);
                                    }

                                    @Override
                                    protected void onPostExecute(Uri returnUri) {
                                        item.setIcon(R.drawable.ic_favorite_black_24dp);
                                        if (mToast != null) {
                                            mToast.cancel();
                                        }
                                        mToast = Toast.makeText(getActivity(),
                                                getString(R.string.added_to_favorites), Toast.LENGTH_SHORT);
                                        mToast.show();
                                    }
                                }.execute();
                            }
                        }
                    }.execute();
                }
                break;
            case R.id.action_share:
                if(items != null){
                updateShareActionProvider(items);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private void updateShareActionProvider(Beeritems items) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, items.getTitle());
        sharingIntent.putExtra(Intent.EXTRA_TEXT, items.getTags() + " : "
                + items.getProducer());
        startActivity(Intent.createChooser(sharingIntent, "sharing Option"));
    }

    @Override
    public void Stores(Store stores, int position) {
        Intent intent = new Intent(getActivity(), StoreActivity.class);
        intent.putExtra(StoreActivity.ARG_STORE, stores);
        startActivity(intent);
    }
}