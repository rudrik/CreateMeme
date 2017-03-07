package com.apptechx.creatememe.Activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import com.apptechx.creatememe.Fragments.fraImagePager;
import com.apptechx.creatememe.R;

/**
 * Created by AppTechX on 10/13/2014.
 */
public class actImagePager extends ActionBarActivity {
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_pager);

        String tag;
        tag = fraImagePager.class.getSimpleName();
        Fragment fr = getSupportFragmentManager().findFragmentByTag(tag);
        if (fr == null) {
            fr = new fraImagePager();
            fr.setArguments(getIntent().getExtras());
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.imageFrameLayout, fr, tag).commit();
        createActionBarMenu();
    }

    private void createActionBarMenu() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Enabling Up / Back navigation
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

    }
}