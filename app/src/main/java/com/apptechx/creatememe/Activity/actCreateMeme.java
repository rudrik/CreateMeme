package com.apptechx.creatememe.Activity;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import com.apptechx.creatememe.Fragments.fraCreateMeme;
import com.apptechx.creatememe.R;


/**
 * Created by AppTechX on 10/15/2014.
 */
public class actCreateMeme extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_meme_layout);
        String tag;
        tag = fraCreateMeme.class.getSimpleName();
        Fragment fr = getSupportFragmentManager().findFragmentByTag(tag);
        if (fr == null) {
            fr = new fraCreateMeme();
            fr.setArguments(getIntent().getExtras());
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.createMemeLayout, fr, tag).commit();
        createActionBarMenu();
    }
//
    private void createActionBarMenu() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getIntent().getStringExtra("TemplateName"));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}