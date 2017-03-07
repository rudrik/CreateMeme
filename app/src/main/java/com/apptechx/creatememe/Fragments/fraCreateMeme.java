package com.apptechx.creatememe.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.apptechx.creatememe.Activity.actTemplateList;
import com.apptechx.creatememe.R;
import com.apptechx.creatememe.Utils.BitmapProcess;
import com.apptechx.creatememe.Utils.Constants;
import com.apptechx.creatememe.Utils.CreateMEME;
import com.apptechx.creatememe.Utils.Utils;
//import com.fourmob.colorpicker.ColorPickerDialog;
//import com.fourmob.colorpicker.ColorPickerSwatch;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.myjson.Gson;
import com.google.myjson.reflect.TypeToken;


import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by AppTechX on 9/26/2014.
 */
public class fraCreateMeme extends Fragment implements View.OnClickListener {

    private EditText EDT_TOP, EDT_BOT;
    private Button BTN_CREATE, BTN_PREVIEW, BTN_SELECTTOPSIZE, BTN_SELECTBOTSIZE;

    private ImageView IMG_CREATED;
    private Utils UTILS;
    private List<String> LST_MYIMAGE;

    private String TemplateName, TemplateImagePath;
    private int TemplateImageID;
    private int IMAGE_ID;
    private float TOPSIZE, BOTSIZE;
    private String IMAGE_PATH = "";
    //    private ColorPickerDialog COLORPICKER_DIALOGE;
    private int SELECTED_COLOR = Color.WHITE;
    private Bitmap bitmapImage;
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.create_meme_page, container, false);
        TemplateImageID = getArguments().getInt("TemplateImageID", 0);
        TemplateName = getArguments().getString("TemplateName");
        TemplateImagePath = getArguments().getString("TemplateImagePath");

        EDT_TOP = (EditText) returnView.findViewById(R.id.edtTop);
        EDT_BOT = (EditText) returnView.findViewById(R.id.edtBot);

        BTN_CREATE = (Button) returnView.findViewById(R.id.btnCreate);
        BTN_PREVIEW = (Button) returnView.findViewById(R.id.btnPreview);
        BTN_SELECTTOPSIZE = (Button) returnView.findViewById(R.id.btnSelectTopSize);
        BTN_SELECTBOTSIZE = (Button) returnView.findViewById(R.id.btnSelectBotSize);
        BTN_CREATE.setOnClickListener(this);
        BTN_PREVIEW.setOnClickListener(this);
        BTN_SELECTTOPSIZE.setOnClickListener(this);
        BTN_SELECTBOTSIZE.setOnClickListener(this);

        IMG_CREATED = (ImageView) returnView.findViewById(R.id.imgBaka);
        UTILS = new Utils(getActivity().getApplicationContext());
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        bitmapImage = BitmapFactory.decodeResource(getActivity().getResources(), TemplateImageID, options);
//        IMG_CREATED.setImageResource(TemplateImageID);
        IMG_CREATED.setImageBitmap(bitmapImage);

        if (!TemplateImagePath.equals("")) {
            bitmapImage = BitmapProcess.decodeSampledBitmapFromPah(TemplateImagePath, 500, 500);
            IMG_CREATED.setImageBitmap(bitmapImage);
        }
        TOPSIZE = getActivity().getResources().getDimension(R.dimen.CodeFont);
        BOTSIZE = getActivity().getResources().getDimension(R.dimen.CodeFont);
        // region GetMyImage
        String listMyImages = UTILS.getPreference(getActivity().getResources().getString(R.string.myImageLst));
        if (!listMyImages.equals("")) {
            Type collectionType = new TypeToken<Collection<String>>() {
            }.getType();
            LST_MYIMAGE = new Gson().fromJson(listMyImages,
                    collectionType);
        } else {
            LST_MYIMAGE = new ArrayList<String>();
        }
        // endregion

        setHasOptionsMenu(true);
//        setColorPicker();

        EDT_TOP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    CreateMEME createMEME;
                    File path;
                    createMEME = CreateMEME.GetInstance();
                    path = createMEME.CreateMEME(getActivity(), EDT_TOP.getText().toString().toUpperCase(), TOPSIZE
                            , EDT_BOT.getText().toString().toUpperCase(), BOTSIZE, true, TemplateImageID,
                            TemplateName, IMAGE_ID, SELECTED_COLOR, bitmapImage);
                    IMAGE_PATH = path.getAbsolutePath();
                    setPic(path);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        EDT_BOT.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    CreateMEME createMEME;
                    File path;
                    createMEME = CreateMEME.GetInstance();
                    path = createMEME.CreateMEME(getActivity(), EDT_TOP.getText().toString().toUpperCase(), TOPSIZE
                            , EDT_BOT.getText().toString().toUpperCase(), BOTSIZE, true, TemplateImageID,
                            TemplateName, IMAGE_ID, SELECTED_COLOR, bitmapImage);
                    IMAGE_PATH = path.getAbsolutePath();
                    setPic(path);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    return true;
                }
                return false;
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

//    private void setColorPicker() {
//        COLORPICKER_DIALOGE = new ColorPickerDialog();
//        COLORPICKER_DIALOGE.initialize(R.string.dialog_title, new int[]{Color.WHITE, Color.CYAN, Color.LTGRAY,
//                        Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.GRAY, Color.YELLOW},
//                Color.BLACK, 3, 2);
//        COLORPICKER_DIALOGE.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
//
//            @Override
//            public void onColorSelected(int color) {
////                Toast.makeText(getActivity(), "selectedColor : " + color, Toast.LENGTH_SHORT).show();
//                SELECTED_COLOR = color;
//            }
//        });
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        MenuInflater mInflater = new MenuInflater(getActivity().getApplicationContext());
        inflater.inflate(R.menu.create_meme_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_ShareImage) {
            if (!IMAGE_PATH.equals("") && !IMAGE_PATH.contains("cache")) {
//                Toast.makeText(getActivity().getApplicationContext(), "Option Selected..!!", Toast.LENGTH_SHORT).show();
                Uri screenshotUri = Uri.parse(IMAGE_PATH);
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Please create meme first.", Toast.LENGTH_SHORT).show();
            }
        }
        if (item.getItemId() == R.id.action_ChooseColor) {
//            COLORPICKER_DIALOGE.show(getActivity().getSupportFragmentManager(), "colorpicker");
            colorpicker();
        }
        if (item.getItemId() == android.R.id.home) {
//            Intent _intent = new Intent(getActivity(), actTemplateList.class);
//            startActivity(_intent);
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void colorpicker() {
        //     initialColor is the initially-selected color to be shown in the rectangle on the left of the arrow.
        //     for example, 0xff000000 is black, 0xff0000ff is blue. Please be aware of the initial 0xff which is the alpha.

        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(), SELECTED_COLOR, new OnAmbilWarnaListener() {

            // Executes, when user click Cancel button
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            // Executes, when user click OK button
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                SELECTED_COLOR = color;
//                Toast.makeText(getActivity().getBaseContext(), "Selected Color : " + color, Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
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

    private void setPic(String PATH) {

        Bitmap bitmap = BitmapProcess.decodeSampledBitmapFromPah(PATH, 800, 800);
        IMG_CREATED.setImageBitmap(bitmap);
        LST_MYIMAGE.add(PATH);
        String listMyImages = new Gson().toJson(LST_MYIMAGE);
        UTILS.setPreference(getActivity().getResources().getString(R.string.myImageLst), listMyImages);
        Toast.makeText(getActivity().getApplicationContext(), "Meme Created SuccessFully", Toast.LENGTH_SHORT).show();
        IMAGE_ID = IMAGE_ID + 1;
        UTILS.setIntPrefrences(Constants.Extra.IMAGE_COUNT, IMAGE_ID);
//        Intent intent = new Intent(getActivity(), actMain.class);
//        intent.putExtra(Constants.Extra.FRAGMENT_INDEX, fraMemeTemplate.INDEX);
//        startActivity(intent);
//        getActivity().finish();
    }

    private void setPic(File imgFile) {
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        IMG_CREATED.setImageBitmap(myBitmap);
    }

    @Override
    public void onClick(View view) {
        CreateMEME createMEME;
        File path;
        switch (view.getId()) {
            case R.id.btnCreate:
                IMG_CREATED.setImageDrawable(null);
                createMEME = CreateMEME.GetInstance();
                IMAGE_ID = UTILS.getIntPreference(Constants.Extra.IMAGE_COUNT);
                path = createMEME.CreateMEME(getActivity(), EDT_TOP.getText().toString().toUpperCase(), TOPSIZE,
                        EDT_BOT.getText().toString().toUpperCase(), BOTSIZE, false, TemplateImageID,
                        TemplateName, IMAGE_ID, SELECTED_COLOR, bitmapImage);
                IMAGE_PATH = path.getAbsolutePath();
                setPic(path.getAbsolutePath());
                break;
            case R.id.btnPreview:
                IMG_CREATED.setImageDrawable(null);
                createMEME = CreateMEME.GetInstance();
                path = createMEME.CreateMEME(getActivity(), EDT_TOP.getText().toString().toUpperCase(), TOPSIZE
                        , EDT_BOT.getText().toString().toUpperCase(), BOTSIZE, true, TemplateImageID,
                        TemplateName, IMAGE_ID, SELECTED_COLOR, bitmapImage);
                IMAGE_PATH = path.getAbsolutePath();
                setPic(path);
                break;
            case R.id.btnSelectTopSize:
                new DialogFontSize(getActivity(), "TOP").show();
                break;
            case R.id.btnSelectBotSize:
                new DialogFontSize(getActivity(), "BOT").show();
                break;
        }
    }

    public class DialogFontSize extends Dialog {

        Context CONTEXT;
        private SeekBar SEEK_BAR;
        private TextView TV_TEXT;
        int progressChanged = 25;
        private String TYPE;


        public DialogFontSize(Context context, String type) {
            super(context);
            CONTEXT = context;
            TYPE = type;

            if (TYPE.equals("TOP")) {
                progressChanged = (int) TOPSIZE;
                this.setTitle("Change Top Font Size");
            } else {
                progressChanged = (int) BOTSIZE;
                this.setTitle("Change Bot Font Size");
            }
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_fontsize);
            SEEK_BAR = (SeekBar) findViewById(R.id.seekBar);
            TV_TEXT = (TextView) findViewById(R.id.tvSampleText);

            Typeface font = Typeface.createFromAsset(CONTEXT.getAssets(), "black.otf");
            TV_TEXT.setTypeface(font);
            findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TYPE.equals("TOP")) {
                        TOPSIZE = progressChanged;
                    } else {
                        BOTSIZE = progressChanged;
                    }
                    dismiss();
                    BTN_PREVIEW.performClick();
                }
            });

            findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            SEEK_BAR.setMax(120);
            TV_TEXT.setTextSize(progressChanged);
            SEEK_BAR.setProgress(progressChanged);
            SEEK_BAR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    progressChanged = progress;
                    TV_TEXT.setTextSize(progressChanged);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (progressChanged < 10) {
                        progressChanged = 10;
                        seekBar.setProgress(progressChanged);
                    }
                }
            });
        }
    }

}