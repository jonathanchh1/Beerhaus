package com.emi.jonat.beerhaus.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emi.jonat.beerhaus.Activities.DetailActivity;
import com.emi.jonat.beerhaus.R;
import com.emi.jonat.beerhaus.Models.Store;
import com.emi.jonat.beerhaus.Activities.StoreActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jonat on 1/2/2018.
 */

public class BeerStoreFragment  extends Fragment {
    public static String LOG_TAG = BeerStoreFragment.class.getSimpleName();
    LayoutInflater mLayoutInflater;
    @BindView(R.id.store_name_title)
    TextView Store_name;

    @BindView(R.id.store_title)
    TextView mStoreTitle;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.postal_code)
    TextView postal_code;
    @BindView(R.id.telephone)
    TextView telephone;
    @BindView(R.id.products)
    TextView products;
    @BindView(R.id.inventory)
    TextView inventory;
    @BindView(R.id.update_store)
    TextView update_store;
    @BindView(R.id.inventory_price)
    TextView inventory_price;
    @BindView(R.id.fax)
    TextView fax;
    @BindView(R.id.bilingual_services)
    TextView bilingual;
    @BindView(R.id.tasting_bar)
    TextView tasting_bar;

    @BindView(R.id.detail_store_name)
    TextView mdetail_store_name;
    @BindView(R.id.address_textview)
    TextView mddress_textview;
    @BindView(R.id.detail_update_store_textview)
    TextView update_store_detail;
    @BindView(R.id.detail_fax_textview)
    TextView mdetail_fax_textview;

    @BindView(R.id.detail_city_textview)
    TextView mdetail_city_textview;
    @BindView(R.id.detail_postal_code_textview)
    TextView mdetail_postal_code;
    @BindView(R.id.detail_telephone_textview)
    TextView mdetail_telephone;
    @BindView(R.id.detail_product_textview)
    TextView mdetail_product_textview;
    @BindView(R.id.detail_inventory_count_textview)
    TextView mdetail_inventory_textview;
    @BindView(R.id.detail_inventory_price_textview)
    TextView mdetail_inventory_price;
    @BindView(R.id.detail_bilingual_content)
    TextView mdetail_bilingual;
    @BindView(R.id.detail_tasting_content)
    TextView mdetail_tasting_bar;
    private Store mStore = new Store();
    View rootView;

    public BeerStoreFragment(){
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
        ImageView backdrop = ((ImageView) activity.findViewById(R.id.cover_image));
        if (backdrop != null) {
            backdrop.setImageDrawable(getResources().getDrawable(R.drawable.ic_beerhause_background));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mLayoutInflater = inflater;

        Bundle arguments = getArguments();
        Intent intent = getActivity().getIntent();

        if (arguments != null || intent != null && intent.hasExtra(StoreActivity.ARG_STORE)) {

            rootView = mLayoutInflater.inflate(R.layout.store_detail_content, container, false);
            if (arguments != null) {
                mStore = getArguments().getParcelable(StoreActivity.ARG_STORE);
            } else {
                mStore = intent.getParcelableExtra(StoreActivity.ARG_STORE);
            }
            ButterKnife.bind(this, rootView);
            Store_name.setText(mStore.getTitle());
        }

        getViews(mStore, rootView);
        return rootView;
    }

    private void getViews(Store items, View view) {
        if(items != null) {
            mdetail_store_name.setText(getResources().getString(R.string.name));
            mStoreTitle.setText(items.getTitle());
            mddress_textview.setText(getResources().getString(R.string.address));
            address.setText(items.getAddress());
            mdetail_city_textview.setText(getResources().getString(R.string.city));
            city.setText(items.getCity());
            mdetail_postal_code.setText(getResources().getString(R.string.postal_code));
            postal_code.setText(items.getPostal());
            mdetail_telephone.setText(getResources().getString(R.string.telephone));
            telephone.setText(items.getTelephone());
            mdetail_product_textview.setText(getResources().getString(R.string.product_count));
            products.setText(String.valueOf(items.getProducts()));
            mdetail_inventory_textview.setText(getResources().getString(R.string.inventory_count));
            inventory.setText(String.valueOf(items.getInventory()));
            update_store_detail.setText(getResources().getString(R.string.update));
            update_store.setText(formatDate(items.getUpdate()));
            mdetail_inventory_price.setText(getResources().getString(R.string.inventory_price));
            inventory_price.setText(String.valueOf(items.getInventory_price()));
            mdetail_fax_textview.setText(getResources().getString(R.string.fax));
            fax.setText(items.getFax());
            mdetail_bilingual.setText(getResources().getString(R.string.bilingual));
            if(items.isBilingual_services()) {
                bilingual.setText(getResources().getString(R.string.bilingualx));
            }else{
                bilingual.setText(getResources().getString(R.string.unavailabledata));
            }
            mdetail_tasting_bar.setText(getResources().getString(R.string.tasting));
            if(items.isTastingbar()) {
                tasting_bar.setText(getResources().getString(R.string.tastingx));
            }else{
                tasting_bar.setText(getResources().getString(R.string.unavailabledata));
            }
        }
        assert items != null;
        Log.d(LOG_TAG, "beer detail: " + items.getUpdate() + items.getTitle() + items.getProducts() + items.getAddress());

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

}
