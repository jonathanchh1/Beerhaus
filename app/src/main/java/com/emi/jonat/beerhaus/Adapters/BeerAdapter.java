package com.emi.jonat.beerhaus.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.emi.jonat.beerhaus.Models.Beeritems;
import com.emi.jonat.beerhaus.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by jonat on 6/25/2017.
 */

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.CustomViewHolder> implements Filterable {
    private ArrayList<Beeritems> mListfilterable;
    private Context mContext;
    private final Beeritems beeritems = new Beeritems();
    private Callbacks mCallbacks;
    private int rowLayout;
    private String errorMsg;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private ArrayList<Beeritems> itemsList;
    public BeerAdapter(Context context, int rowLayout, final ArrayList<Beeritems> mListfilterable, Callbacks callbacks) {
        this.mContext = context;
        this.rowLayout = rowLayout;
        this.mCallbacks = callbacks;
        this.mListfilterable = new ArrayList<>();
        this.itemsList = mListfilterable;




    }


    /*--------------------------------------------------pagination----------------------------------------------*/

    public void addpages(Beeritems r) {
        mListfilterable.add(r);
        notifyItemInserted(mListfilterable.size() - 1);
    }

    public void addAll(ArrayList<Beeritems> Results) {
        for (Beeritems result : Results) {
            addpages(result);
        }
    }

    public void removes(Beeritems r) {
        int position = mListfilterable.indexOf(r);
        if (position > -1) {
            mListfilterable.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            removes(getItem(0));
        }
    }

    public  Beeritems getItem(int position) {
        if(mListfilterable.size() == 0){
            return null;
        }
            return mListfilterable.get(position);
        }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        addpages(new Beeritems());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mListfilterable.size() - 1;
        Beeritems result = getItem(position);

        if (result != null) {
            mListfilterable.remove(position);
            notifyItemRemoved(position);
        }
    }


    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(mListfilterable.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    /*----------------------------------------------------------end of pagination---------------------------------------------------*/


    public void setData(ArrayList<Beeritems> data) {
        remove();
        for (Beeritems items : data) {
            add(items);
        }
    }

    public void remove() {
        synchronized (beeritems) {
            mListfilterable.clear();
        }
        notifyDataSetChanged();
    }

    public void add(Beeritems items) {
        synchronized (beeritems) {
            mListfilterable.add(items);
        }
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListfilterable = itemsList;
                } else {
                    ArrayList<Beeritems> filteredList = new ArrayList<>();
                    for (Beeritems row : itemsList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || (row.getTitle().toUpperCase().contains(charString.toUpperCase())) ){
                            filteredList.add(row);
                        }
                    }

                    mListfilterable = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListfilterable;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListfilterable = (ArrayList<Beeritems>) filterResults.values;
                notifyDataSetChanged();
            }
        };    }

    public interface Callbacks{
        void onTaskCompleted(Beeritems items, int position);
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final Beeritems feedItem = mListfilterable.get(i);
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
        if (feedItem.getTitle() != null) {
            customViewHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));
        }

    }


    @Override
    public int getItemCount() {
    return mListfilterable == null ? 0 : mListfilterable.size();
    }






   class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
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
