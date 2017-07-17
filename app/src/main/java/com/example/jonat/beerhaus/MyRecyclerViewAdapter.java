package com.example.jonat.beerhaus;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jonat.beerhaus.data.BeerContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jonat on 6/25/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private ArrayList<Beeritems> feedItemList;
    private Context mContext;
    private Beeritems beeritems = new Beeritems();
    private Callbacks mCallbacks;

    public MyRecyclerViewAdapter(Context context, ArrayList<Beeritems> feedItemList, Callbacks callbacks) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.mCallbacks = callbacks;
    }




    public void setData(ArrayList<Beeritems> beerlist){
        for(Beeritems items: beerlist){
            add(items);
        }

    }

    public void add(Beeritems items) {
        synchronized (beeritems){
            feedItemList.add(items);
        }
    }



    public interface Callbacks{
        void onTaskCompleted(Beeritems items, int position);
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final Beeritems feedItem = feedItemList.get(i);
        customViewHolder.items = feedItem;

        //Render image using Picasso library
        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
            Picasso.with(mContext).load(feedItem.getThumbnail())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(customViewHolder.imageView);
        }

        customViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onTaskCompleted(feedItem, customViewHolder.getAdapterPosition());
            }
        });

        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        public Beeritems items;
        View mView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
            mView = view;
        }
    }

}
