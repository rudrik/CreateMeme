package com.apptechx.creatememe.Fragments;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.*;
import android.widget.*;
import com.apptechx.creatememe.Activity.actCreateMeme;
import com.apptechx.creatememe.R;
import com.apptechx.creatememe.Utils.ImageList;
import com.apptechx.creatememe.Utils.Utils;
import com.apptechx.creatememe.model.TemplateItem;
import com.google.android.gms.ads.AdRequest;
import com.google.myjson.Gson;
import com.google.myjson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by AppTechX on 10/15/2014.
 */
public class fraTemplateList extends BaseFragment {

    private ListView lstTemplate;
    private LSTAdapter ADAPTER;
    private List<TemplateItem> LIST_TEMPLATE, LIST_MAIN;
    private Utils UTILS;
    private List<String> LST_MYFAVTEMP;
    private AdView mAdView;

//    private final Integer[] imageID = {R.drawable.asian,
//            R.drawable.bad_luck_brian,
//            R.drawable.confession_bear,
//            R.drawable.first_world_prob,
//            R.drawable.futurama,
//            R.drawable.i_dont_always,
//            R.drawable.jo_baka,
//            R.drawable.one_does_not,
//            R.drawable.winter_is_coming};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.meme_template_list, container, false);
        lstTemplate = (ListView) returnView.findViewById(R.id.lstMemeTemplate);
        UTILS = new Utils(getActivity());
        String listMyFavTemp = UTILS.getPreference(getActivity().getResources().getString(R.string.myFavTempLst));
        if (!listMyFavTemp.equals("")) {
            Type collectionType = new TypeToken<Collection<String>>() {
            }.getType();
            LST_MYFAVTEMP = new Gson().fromJson(listMyFavTemp,
                    collectionType);
        } else {
            LST_MYFAVTEMP = new ArrayList<>();
        }
        LIST_TEMPLATE = GenerateTemplate(LST_MYFAVTEMP);
        ADAPTER = new LSTAdapter();
        lstTemplate.setAdapter(ADAPTER);

        lstTemplate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), actCreateMeme.class);
                intent.putExtra("TemplateName", LIST_TEMPLATE.get(i).getTemplateName());
                intent.putExtra("TemplateImageID", LIST_TEMPLATE.get(i).getTemplateImageId());
                intent.putExtra("TemplateImagePath", "");
                startActivity(intent);
            }
        });
        setHasOptionsMenu(true);
        LIST_MAIN = new ArrayList<>(LIST_TEMPLATE);

//        AdView adView = (AdView) returnView.findViewById(R.id.myAdView);
//        adView.setBannerType(AdView.BANNER_TYPE_IN_APP_AD);
//        adView.setBannerAnimation(AdView.ANIMATION_TYPE_FADE);
//        adView.showMRinInApp(false);
//
//        if (adView != null)
//            adView.loadAd();
        mAdView = (AdView) returnView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        return returnView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater = getActivity().getMenuInflater();
//        inflater.inflate(R.menu.template_list_menu, menu);
        getActivity().getMenuInflater().inflate(R.menu.template_list_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                ADAPTER.getFilter().filter(newText);
                System.out.println("on text chnge text: " + newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // this is your adapter that will be filtered
                ADAPTER.getFilter().filter(query);
                System.out.println("on query submit: " + query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {

        }
        return super.onOptionsItemSelected(item);
    }

    private List<TemplateItem> GenerateTemplate(List<String> LST_MYFAVTEMP) {
        List<TemplateItem> lstTemplate = new ArrayList<>();
        for (int i = 0; i < getResources().getStringArray(R.array.template_Name).length; i++) {

            TemplateItem obj = new TemplateItem();
            obj.setTemplateName(getResources().getStringArray(R.array.template_Name)[i]);
            obj.setTemplateImageId(ImageList.imageID[i]);
            obj.setTemplateFav(LST_MYFAVTEMP.contains(getResources().getStringArray(R.array.template_Name)[i]));
            lstTemplate.add(obj);
        }
        return lstTemplate;
    }

    public class LSTAdapter extends BaseAdapter implements Filterable {

        LayoutInflater INFLATOR;

        LSTAdapter() {
            INFLATOR = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return LIST_TEMPLATE.size();
        }

        @Override
        public Object getItem(int i) {
            return LIST_TEMPLATE.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = INFLATOR.inflate(R.layout.meme_template_list_row, null);
                holder = new ViewHolder();
                holder.IMG_FAV = (ImageView) convertView.findViewById(R.id.imgFavNoFav);
                holder.IMG_TEMPLATE = (ImageView) convertView.findViewById(R.id.imgTemplateImage);
                holder.TV_NAME = (TextView) convertView.findViewById(R.id.tvTemplateName);
                convertView.setTag(holder);
                holder.IMG_FAV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    Toast.makeText(getActivity().getApplicationContext(), view.getTag().toString(), Toast.LENGTH_SHORT).show();
                        ImageView _iv = (ImageView) view;
                        int position = (int) _iv.getTag();
                        if (LIST_TEMPLATE.get(position).isTemplateFav()) {
                            LIST_TEMPLATE.get(position).setTemplateFav(false);
                            LST_MYFAVTEMP.remove(LIST_TEMPLATE.get(position).getTemplateName());
                            String listMyImages = new Gson().toJson(LST_MYFAVTEMP);
                            UTILS.setPreference(getActivity().getResources().getString(R.string.myFavTempLst), listMyImages);
                            Toast.makeText(getActivity().getApplicationContext(), "Meme removed from Favourite SuccessFully", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            LIST_TEMPLATE.get(position).setTemplateFav(true);
                            LST_MYFAVTEMP.add(LIST_TEMPLATE.get(position).getTemplateName());
                            String listMyImages = new Gson().toJson(LST_MYFAVTEMP);
                            UTILS.setPreference(getActivity().getResources().getString(R.string.myFavTempLst), listMyImages);
                            Toast.makeText(getActivity().getApplicationContext(), "Meme added from Favourite SuccessFully", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.TV_NAME.setText(LIST_TEMPLATE.get(position).getTemplateName().toUpperCase().trim());
//            final BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 8;
//            Bitmap bm = BitmapFactory.decodeResource(getResources(), LIST_TEMPLATE.get(position).getTemplateImageId(), options);
//            holder.IMG_TEMPLATE.setImageBitmap(bm);
            Picasso.with(getActivity()).load(LIST_TEMPLATE.get(position).getTemplateImageId()).into(holder.IMG_TEMPLATE);
            if (LIST_TEMPLATE.get(position).isTemplateFav()) {
//                holder.IMG_FAV.setImageResource(R.drawable.fav);
                Picasso.with(getActivity()).load(R.drawable.fav).into(holder.IMG_FAV);
            }
            else {
//                holder.IMG_FAV.setImageResource(R.drawable.nofav);
                Picasso.with(getActivity()).load(R.drawable.nofav).into(holder.IMG_FAV);
            }

            holder.IMG_FAV.setTag(position);
            return convertView;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    List<TemplateItem> filteredResult = getFilteredResults(charSequence);
                    FilterResults results = new FilterResults();
                    results.values = filteredResult;
                    results.count = filteredResult.size();
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    LIST_TEMPLATE = (ArrayList<TemplateItem>) filterResults.values;
                    LSTAdapter.this.notifyDataSetChanged();
                }

                private List<TemplateItem> getFilteredResults(CharSequence constraint) {
                    if (constraint.length() == 0) {
                        return LIST_MAIN;
                    }
                    ArrayList<TemplateItem> listResult = new ArrayList<TemplateItem>();
                    for (TemplateItem obj : LIST_MAIN) {
                        if (obj.getTemplateName().toUpperCase().trim().contains(constraint.toString().toUpperCase().trim())) {
                            listResult.add(obj);
                        }
                    }
                    return listResult;
                }
            };
        }
    }


    class ViewHolder {

        private TextView TV_NAME;
        private ImageView IMG_TEMPLATE;
        private ImageView IMG_FAV;
    }
}