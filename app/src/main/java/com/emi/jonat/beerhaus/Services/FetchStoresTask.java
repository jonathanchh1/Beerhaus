package com.emi.jonat.beerhaus.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.emi.jonat.beerhaus.BuildConfig;
import com.emi.jonat.beerhaus.Models.Store;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jonat on 10/5/2016.
 */
public class FetchStoresTask extends AsyncTask<Integer, Void, ArrayList<Store>> {

    public String mSortBy = "stores";
    @SuppressWarnings("unused")
    public static String LOG_TAG = FetchStoresTask.class.getSimpleName();
    private final Listener mListener;

    public FetchStoresTask(Listener listener) {
        mListener = listener;
    }

    @Override
    protected ArrayList<Store> doInBackground(Integer... params) {

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<StoresResponse> call = service.getStoresPages(mSortBy,
                BuildConfig.BEER_API);
        try {
            Response<StoresResponse> response = call.execute();
            if (response.isSuccessful()) {
                StoresResponse storesResponse = response.body();
                Log.d(LOG_TAG, storesResponse.toString());
                return storesResponse.getResults();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "A problem occurred talking to the movie db ", e);
        }


        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Store> stores) {
        if (stores != null) {
            mListener.onFetchFinished(stores);
        } else {
            mListener.onFetchFinished(new ArrayList<Store>());
        }
    }

    /**
     * Interface definition for a callback to be invoked when trailers are loaded.
     */
    public interface Listener {
        void onFetchFinished(ArrayList<Store> stores);
    }
}
