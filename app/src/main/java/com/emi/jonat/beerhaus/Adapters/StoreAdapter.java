package com.emi.jonat.beerhaus.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.jonat.beerhaus.R;
import com.emi.jonat.beerhaus.Models.Store;

import java.util.ArrayList;

/**
 * Created by jonat on 12/31/2017.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    @SuppressWarnings("unused")
    private final static String LOG_TAG = StoreAdapter.class.getSimpleName();
    private final Callbacks mCallbacks;
    private ArrayList<Store> mStores;
    Store storeitems = new Store();

    public StoreAdapter(ArrayList<Store> stores, Callbacks callbacks) {
        mStores = stores;
        mCallbacks = callbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Store store = mStores.get(position);
        final Context context = holder.mView.getContext();

        float paddingLeft = 0;
        if (position == 0) {
            paddingLeft = context.getResources().getDimension(R.dimen.detail_horizontal_padding);
        }

        float paddingRight = 0;
        if (position + 1 != getItemCount()) {
            paddingRight = context.getResources().getDimension(R.dimen.detail_horizontal_padding) / 2;
        }

        holder.mView.setPadding((int) paddingLeft, 0, (int) paddingRight, 0);

        holder.mStores = store;

        holder.Store_name.setText(store.getTitle());
        holder.Location.setText(store.getCity());
        holder.description.setText(store.getAddress());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.Stores(store, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStores.size();
    }

    public void add(ArrayList<Store> stores) {
        mStores.clear();
        mStores.addAll(stores);
        notifyDataSetChanged();
    }

    public void addpages(Store r) {
        mStores.add(r);
        notifyItemInserted(mStores.size() - 1);
    }

    public void addAll(ArrayList<Store> Results) {
        for (Store result : Results) {
            addpages(result);
        }
    }

    public void removes(Store r) {
        int position = mStores.indexOf(r);
        if (position > -1) {
            mStores.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            removes(getItem(0));
        }
    }

    public Store getItem(int position) {
        return mStores.get(position);
    }

    public void add(Store items) {
        synchronized (storeitems) {
            mStores.add(items);
        }

        notifyDataSetChanged();
    }

    public void setmStores(ArrayList<Store> mStores) {
        this.mStores = mStores;
    }


    public ArrayList<Store> getmStores() {
        return mStores;
    }

    public interface Callbacks {
        void Stores(Store stores, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Store mStores;
        TextView Store_name;
        TextView Location;
        TextView description;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            Store_name = (TextView) view.findViewById(R.id.store_name);
            Location = (TextView) view.findViewById(R.id.location);
            description = (TextView) view.findViewById(R.id.description);

        }
    }
}
