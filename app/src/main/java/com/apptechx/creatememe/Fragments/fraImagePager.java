/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.apptechx.creatememe.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.apptechx.creatememe.R;
import com.apptechx.creatememe.Utils.Constants;
import com.apptechx.creatememe.Utils.Utils;
import com.google.myjson.Gson;
import com.google.myjson.reflect.TypeToken;
import com.mqnvnfx.itwsdvr70223.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class fraImagePager extends BaseFragment {

    public static final int INDEX = 3;
    DisplayImageOptions options;
    List<String> LST_CREATED_IMAGE;
    private Utils UTILS;
    private ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        setHasOptionsMenu(true);

        String[] imageUrls = Constants.IMAGES;
        UTILS = new Utils(getActivity().getApplicationContext());
        String listMyImages = UTILS.getPreference(getActivity().getResources().getString(R.string.myImageLst));
        if (!listMyImages.equals("")) {
            Type collectionType = new TypeToken<Collection<String>>() {
            }.getType();
            List<String> LST_TEMP = new Gson().fromJson(listMyImages,
                    collectionType);
            LST_CREATED_IMAGE = new ArrayList<String>();
            for (String path : LST_TEMP) {
                Uri uriSavedImage = Uri.fromFile(new File(path));
                LST_CREATED_IMAGE.add(String.valueOf(uriSavedImage));
            }

        } else {
            LST_CREATED_IMAGE = new ArrayList<String>();
        }

        for (String str : imageUrls) {
            LST_CREATED_IMAGE.add(str);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_image_pager, container, false);
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(new ImageAdapter());
        pager.setCurrentItem(getArguments().getInt(Constants.Extra.IMAGE_POSITION, 0));

        return rootView;
    }

    private class ImageAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        ImageAdapter() {
            inflater = LayoutInflater.from(getActivity());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return LST_CREATED_IMAGE.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
            assert imageLayout != null;
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

            ImageLoader.getInstance().displayImage(LST_CREATED_IMAGE.get(position), imageView, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    String message = null;
                    switch (failReason.getType()) {
                        case IO_ERROR:
                            message = "Input/Output error";
                            break;
                        case DECODING_ERROR:
                            message = "Image can't be decoded";
                            break;
                        case NETWORK_DENIED:
                            message = "Downloads are denied";
                            break;
                        case OUT_OF_MEMORY:
                            message = "Out Of Memory error";
                            break;
                        case UNKNOWN:
                            message = "Unknown error";
                            break;
                    }
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    spinner.setVisibility(View.GONE);
                }
            });

            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        MenuInflater mInflater = new MenuInflater(getActivity().getApplicationContext());
//        mInflater.inflate(R.menu.image_pager_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.image_pager_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_ShareImage) {
//            Toast.makeText(getActivity().getApplicationContext(), "Option Selected..!!", Toast.LENGTH_SHORT).show();
//            Intent shareIntent = new Intent();
//            shareIntent.setAction(Intent.ACTION_SEND);
//            shareIntent.putExtra(Intent.EXTRA_STREAM, LST_CREATED_IMAGE.get(pager.getCurrentItem()));
//            shareIntent.setType("image/*");
//            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
            Uri screenshotUri;
            if (LST_CREATED_IMAGE.get(pager.getCurrentItem()).toString().contains("http")) {
                File file = DiskCacheUtils.findInCache(LST_CREATED_IMAGE.get(pager.getCurrentItem())
                        , ImageLoader.getInstance().getDiskCache());
                File storedFile = storeImage(file);
                screenshotUri = Uri.fromFile(storedFile);
            } else {
                screenshotUri = Uri.parse(LST_CREATED_IMAGE.get(pager.getCurrentItem()));
            }

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("image/*");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            startActivity(Intent.createChooser(sharingIntent, "Share image using"));
        }
        return super.onOptionsItemSelected(item);
    }

    // Detach FragmentTabHost
    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class
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


    private File storeImage(File file) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(":::: ",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }
        try {
            InputStream in = new FileInputStream(file);
            OutputStream out = new FileOutputStream(pictureFile);
            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            Log.d(":::: ", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(":::: ", "Error accessing file: " + e.getMessage());
        }
        return pictureFile;
    }

    //    ** Create a File for saving an image or video */
    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
//        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
//                + "/Android/data/"
//                + getApplicationContext().getPackageName()
//                + "/Files");
        String root = Environment.getExternalStorageDirectory().toString();
        File mediaStorageDir = new File(root + "/Jo_Baka/SentImage");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "image" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

}