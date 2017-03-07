package com.apptechx.creatememe.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.apptechx.creatememe.Activity.actCreateMeme;
import com.apptechx.creatememe.R;
import com.apptechx.creatememe.Utils.BitmapProcess;
import com.apptechx.creatememe.Utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.myjson.Gson;
import com.google.myjson.reflect.TypeToken;
//import com.mqnvnfx.itwsdvr70223.AdView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by AppTechX on 10/30/2014.
 */
public class fraMyTemplateList extends Fragment {
    private Utils UTILS;
    private List<String> LST_MYTEMPLATE;
    private LSTAdapter ADAPTER;
    private ListView lstMYTemplate;
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.my_template_list, container, false);
        lstMYTemplate = (ListView) returnView.findViewById(R.id.lstMyTemplateList);
        UTILS = new Utils(getActivity());
        // region GetMyTemplate
        String listMyImages = UTILS.getPreference(getResources().getString(R.string.myTempLateLst));
        if (!listMyImages.equals("")) {
            Type collectionType = new TypeToken<Collection<String>>() {
            }.getType();
            LST_MYTEMPLATE = new Gson().fromJson(listMyImages,
                    collectionType);
        } else {
            LST_MYTEMPLATE = new ArrayList<>();
        }
        // endregion

        ADAPTER = new LSTAdapter();
        lstMYTemplate.setAdapter(ADAPTER);

        lstMYTemplate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), actCreateMeme.class);
                intent.putExtra("TemplateName", "MyTemplate");
                intent.putExtra("TemplateImagePath", LST_MYTEMPLATE.get(i));
                startActivity(intent);
            }
        });

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


    public class LSTAdapter extends BaseAdapter {

        LayoutInflater INFLATOR;

        LSTAdapter() {
            INFLATOR = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return LST_MYTEMPLATE.size();
        }

        @Override
        public Object getItem(int i) {
            return LST_MYTEMPLATE.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = INFLATOR.inflate(R.layout.my_template_list_row, null);
                holder = new ViewHolder();
                holder.IMG_TEMPLATE = (ImageView) convertView.findViewById(R.id.imgMyTemplateImage);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Bitmap bm = BitmapProcess.decodeSampledBitmapFromPah(LST_MYTEMPLATE.get(position), 50, 50);
            holder.IMG_TEMPLATE.setImageBitmap(bm);
            return convertView;
        }
    }

    class ViewHolder {
        private ImageView IMG_TEMPLATE;
    }
}