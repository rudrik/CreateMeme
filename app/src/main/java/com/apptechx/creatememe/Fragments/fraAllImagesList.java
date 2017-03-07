package com.apptechx.creatememe.Fragments;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.apptechx.creatememe.R;
import com.apptechx.creatememe.Utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.myjson.Gson;
import com.google.myjson.reflect.TypeToken;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by AppTechX on 9/23/2014.
 */
public class fraAllImagesList extends AbsListViewBaseFragment {
    public static final int INDEX = 4;
    private ImageAdapter ADAPTER;
    //private GridView GRD;
    private Button BTN_LOADMORE;
    private ArrayList<String> LST_DATA;
//    private int IMAGECOUNT = 80;
    DisplayImageOptions options;
    public ImageLoader imageLoader;
    private Utils UTILS;
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.all_image, container, false);
//        GRD = (GridView) returnView.findViewById(R.id.gridphoto);
        BTN_LOADMORE = (Button) returnView.findViewById(R.id.loadmore);
//        GRD.setOnScrollListener(this);

//        LST_DATA = new ArrayList<String>();
        UTILS = new Utils(getActivity().getApplicationContext());
        String listMyImages = UTILS.getPreference(getActivity().getResources().getString(R.string.myImageLst));
        if (!listMyImages.equals("")) {
            Type collectionType = new TypeToken<Collection<String>>() {
            }.getType();
            List<String> LST_TEMP = new Gson().fromJson(listMyImages,
                    collectionType);
            LST_DATA = new ArrayList<String>();
            for (String path : LST_TEMP) {
                Uri uriSavedImage = Uri.fromFile(new File(path));
                LST_DATA.add(String.valueOf(uriSavedImage));
            }

        } else {
            LST_DATA = new ArrayList<String>();
        }

//        for (int i = 0; i < IMAGECOUNT; i++) {
//            LST_DATA.add(Constants.IMAGES[i]);
//        }
        ADAPTER = new ImageAdapter();
        listView = (GridView) returnView.findViewById(R.id.gridphoto);
        ((GridView) listView).setAdapter(ADAPTER);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
//        if (GRD.getLastVisiblePosition() + 1 == IMAGECOUNT) {
//            BTN_LOADMORE.setVisibility(View.VISIBLE); // Load More Button
//        }
                Log.d(":::: ", String.valueOf(listView.getLastVisiblePosition() + 1));
//                if ((listView.getLastVisiblePosition() + 1) == IMAGECOUNT) {
//                    BTN_LOADMORE.setVisibility(View.VISIBLE); // Load More Button
//                }
            }
        });
//        GRD.setAdapter(ADAPTER);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startImagePagerActivity(position);
            }
        });
        BTN_LOADMORE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                IMAGECOUNT++;
//                if (Constants.IMAGES.length > IMAGECOUNT) {
//                    LST_DATA.add(Constants.IMAGES[IMAGECOUNT]);
//                    ADAPTER.notifyDataSetChanged();
//                }
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.activity_main_actions, menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_SyncImage:
//                Toast.makeText(getActivity().getApplicationContext(), "Option Selected..!!", Toast.LENGTH_SHORT).show();
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ImageAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        ImageAdapter() {
            inflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return LST_DATA.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.grid_row, parent, false);
                holder = new ViewHolder();
                assert view != null;
                holder.imageView = (ImageView) view.findViewById(R.id.imgJoBaka);
                holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            imageLoader.displayImage(LST_DATA.get(position), holder.imageView, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    holder.progressBar.setProgress(0);
                    holder.progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.progressBar.setVisibility(View.GONE);
                }
            }, new ImageLoadingProgressListener() {
                @Override
                public void onProgressUpdate(String imageUri, View view, int current, int total) {
                    holder.progressBar.setProgress(Math.round(100.0f * current / total));
                }
            });

            return view;
        }
    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }


    // Detach FragmentTabHost
    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = android.support.v4.app.Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Remove FragmentTabHost
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}