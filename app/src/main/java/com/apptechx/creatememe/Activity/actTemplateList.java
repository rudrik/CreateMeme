package com.apptechx.creatememe.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.apptechx.creatememe.Fragments.fraAllImagesList;
import com.apptechx.creatememe.Fragments.fraFavImageList;
import com.apptechx.creatememe.Fragments.fraMyTemplateList;
import com.apptechx.creatememe.Fragments.fraTemplateList;
import com.apptechx.creatememe.R;
import com.apptechx.creatememe.Utils.MainMenu;
import com.apptechx.creatememe.adapter.MenuAdapter;
import com.apptechx.creatememe.adapter.TitleNavigationAdapter;
import com.apptechx.creatememe.model.SpinnerNavItem;
import com.mqnvnfx.itwsdvr70223.AdConfig;
import com.mqnvnfx.itwsdvr70223.AdListener;
import com.mqnvnfx.itwsdvr70223.EulaListener;

import net.equasoft.ratingreminder.RatingReminder;

import java.util.ArrayList;

/**
 * Created by AppTechX on 10/21/2014.
 */
public class actTemplateList extends ActionBarActivity {//implements AdListener, EulaListener {

    private ActionBar actionBar;
    private ArrayList<SpinnerNavItem> navSpinner;
    private TitleNavigationAdapter adapter;
    public static final int INDEX = 0;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mTitles;
    private boolean backPressedToExitOnce = false;
    private Toast toast = null;

    private boolean enableCaching = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_list);

        //region ToolBarRegion
//        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
////        setSupportActionBar(toolbar);
//
//        // Set an OnMenuItemClickListener to handle menu item clicks
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                // Handle the menu item
//                if (item.getItemId() == R.id.action_all) {
//                    FragmentChange(0);
//                } else if (item.getItemId() == R.id.action_myfav) {
//                    FragmentChange(1);
//                } else if (item.getItemId() == R.id.action_custommeme) {
//                    FragmentChange(2);
//                } else if (item.getItemId() == R.id.action_createdMeme) {
//                    FragmentChange(3);
//                } else if (item.getItemId() == R.id.action_savedMeme) {
//                    FragmentChange(4);
//                }
//                return true;
//            }
//        });
//
//
//        // Inflate a menu to be displayed in the toolbar
//        toolbar.inflateMenu(R.menu.action_menu);
//        FragmentChange(0);
        //endregion
//        setActionBar();

//        AdConfig.setAppId(301072);
//        AdConfig.setApiKey("1346129446702238128");
//        AdConfig.setEulaListener(this);
//        AdConfig.setAdListener(this);
//        AdConfig.setCachingEnabled(enableCaching);
//        AdConfig.setTestMode(true);


        mTitle = mDrawerTitle = getTitle();
        mTitles = getResources().getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        MainMenu.LoadModel(this);
        String[] ids = new String[MainMenu.Items.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = Integer.toString(i + 1);
        }
// set up the drawer's list view with items using a custom adapter and click listener
        MenuAdapter adapter = new MenuAdapter(this, ids);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
// set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
// enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
// ActionBarDrawerToggle ties together the the proper interactions
// between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            FragmentChange(0);
        }

        RatingReminder reminder = new RatingReminder(this);
        reminder.setAppName(getString(R.string.app_name));
        reminder.process();
    }

    private void setActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

////        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//        navSpinner = new ArrayList<>();
//        navSpinner.add(new SpinnerNavItem("All", R.drawable.ic_drawer));
//        navSpinner.add(new SpinnerNavItem("My Fav", R.drawable.ic_drawer));
//        navSpinner.add(new SpinnerNavItem("Create Custom Meme", R.drawable.ic_drawer));
//        navSpinner.add(new SpinnerNavItem("Created Meme", R.drawable.ic_drawer));
//        navSpinner.add(new SpinnerNavItem("Saved Meme", R.drawable.ic_drawer));
//        adapter = new TitleNavigationAdapter(getApplicationContext(), navSpinner);
//        actionBar.setListNavigationCallbacks(adapter, this);
    }

//    @Override
//    public boolean onNavigationItemSelected(int i, long l) {
//        String tag;
//        Fragment fr;
//        switch (i) {
//            default:
//            case 0:
//                tag = fraTemplateList.class.getSimpleName();
//                fr = getSupportFragmentManager().findFragmentByTag(tag);
//                if (fr == null) {
//                    fr = new fraTemplateList();
//                    fr.setArguments(getIntent().getExtras());
//                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.templateFrameLayout, fr, tag).commitAllowingStateLoss();
//                break;
//            case 1:
//                tag = fraFavImageList.class.getSimpleName();
//                fr = getSupportFragmentManager().findFragmentByTag(tag);
//                if (fr == null) {
//                    fr = new fraFavImageList();
//                    fr.setArguments(getIntent().getExtras());
//                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.templateFrameLayout, fr, tag).commitAllowingStateLoss();
//                break;
//            case 2:
//                Intent intent = new Intent(actTemplateList.this, actCreateTemplate.class);
//                startActivity(intent);
//                finish();
//                break;
//            case 3:
//                //Code for displaying created Template
//                tag = fraMyTemplateList.class.getSimpleName();
//                fr = getSupportFragmentManager().findFragmentByTag(tag);
//                if (fr == null) {
//                    fr = new fraMyTemplateList();
//                    fr.setArguments(getIntent().getExtras());
//                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.templateFrameLayout, fr, tag).commitAllowingStateLoss();
//                break;
//            case fraAllImagesList.INDEX:
//                tag = fraAllImagesList.class.getSimpleName();
//                fr = getSupportFragmentManager().findFragmentByTag(tag);
//                if (fr == null) {
//                    fr = new fraAllImagesList();
//                    fr.setArguments(getIntent().getExtras());
//                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.templateFrameLayout, fr, tag).commitAllowingStateLoss();
//                break;
//        }
//        return false;
//    }

    public void FragmentChange(int index) {
        String tag;
        Fragment fr;
        switch (index) {
            default:
            case 0:
                tag = fraTemplateList.class.getSimpleName();
                fr = getSupportFragmentManager().findFragmentByTag(tag);
                if (fr == null) {
                    fr = new fraTemplateList();
                    fr.setArguments(getIntent().getExtras());
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.templateFrameLayout, fr, tag).commit();
                break;
            case 1:
                tag = fraFavImageList.class.getSimpleName();
                fr = getSupportFragmentManager().findFragmentByTag(tag);
                if (fr == null) {
                    fr = new fraFavImageList();
                    fr.setArguments(getIntent().getExtras());
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.templateFrameLayout, fr, tag).commit();
                break;
            case 2:
                Intent intent = new Intent(actTemplateList.this, actCreateTemplate.class);
                startActivity(intent);
                finish();
                break;
            case 3:
                //Code for displaying created Template
                tag = fraMyTemplateList.class.getSimpleName();
                fr = getSupportFragmentManager().findFragmentByTag(tag);
                if (fr == null) {
                    fr = new fraMyTemplateList();
                    fr.setArguments(getIntent().getExtras());
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.templateFrameLayout, fr, tag).commit();
                break;
            case fraAllImagesList.INDEX:
                tag = fraAllImagesList.class.getSimpleName();
                fr = getSupportFragmentManager().findFragmentByTag(tag);
                if (fr == null) {
                    fr = new fraAllImagesList();
                    fr.setArguments(getIntent().getExtras());
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.templateFrameLayout, fr, tag).commit();
                break;
        }
        mDrawerList.setItemChecked(index, true);
        setTitle(mTitles[index]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (item.getItemId() == android.R.id.home) {
//            Intent _intent = new Intent(actTemplateList.this, actMain.class);
//            _intent.putExtra(Constants.Extra.FRAGMENT_INDEX, fraAllImagesList.class);
//            startActivity(_intent);
//            finish();
//        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (backPressedToExitOnce) {
            super.onBackPressed();
        } else {
            this.backPressedToExitOnce = true;
            showToast("Press again to exit");
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPressedToExitOnce = false;
                }
            }, 2000);
        }

    }

    /**
     * Created to make sure that you toast doesn't show miltiple times, if user pressed back
     * button more than once.
     *
     * @param message Message to show on toast.
     */
    private void showToast(String message) {
        if (this.toast == null) {
            // Create toast if found null, it would he the case of first call only
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else if (this.toast.getView() == null) {
            // Toast not showing, so create new one
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else {
            // Updating toast message is showing
            this.toast.setText(message);
        }

        // Showing toast finally
        this.toast.show();
    }

    /**
     * Kill the toast if showing. Supposed to call from onPause() of activity.
     * So that toast also get removed as activity goes to background, to improve
     * better app experiance for user
     */
    private void killToast() {
        if (this.toast != null) {
            this.toast.cancel();
        }
    }

    @Override
    protected void onPause() {
        killToast();
        super.onPause();
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FragmentChange(position);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
// Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
// Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

//    @Override
//    public void optinResult(boolean isAccepted) {
//        if (isAccepted)
//            showToast("You have accepted the EULA.");
//        else
//            showToast("You have not accepted the EULA.");
//    }
//
//
//    @Override
//    public void showingEula() {
//        showToast("EULA is showing.");
//    }
//
//    @Override
//    public void onAdCached(AdConfig.AdType adType) {
//        showToast("Ad cached: " + adType);
//
//    }
//
//    @Override
//    public void onIntegrationError(String errorMessage) {
//        showToast("Integration Error: " + errorMessage);
//
//    }
//
//    @Override
//    public void onAdError(String errorMessage) {
//        showToast("Ad error: " + errorMessage);
//    }
//
//    @Override
//    public void noAdListener() {
//        showToast("No ad received");
//    }
//
//    @Override
//    public void onAdShowing() {
//        showToast("Showing SmartWall ad");
//    }
//
//    @Override
//    public void onAdClosed() {
//        showToast("Ad closed");
//    }
//
////    private void showToast(String message) {
////        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
////
////    }
//
//    @Override
//    public void onAdLoadingListener() {
//        showToast("Ad is loaading");
//    }
//
//    @Override
//    public void onAdLoadedListener() {
//        showToast("Ad  is loaded");
//    }
//
//    @Override
//    public void onCloseListener() {
//        showToast("Ad closed");
//    }
//
//    @Override
//    public void onAdExpandedListner() {
//        showToast("Ad onAdExpandedListner");
//    }
//
//    @Override
//    public void onAdClickedListener() {
//        showToast("Ad onAdClickedListener");
//    }

}