package com.apptechx.creatememe.Activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.apptechx.creatememe.R;
import com.apptechx.creatememe.Utils.BitmapProcess;
import com.apptechx.creatememe.Utils.Constants;
import com.apptechx.creatememe.Utils.CreateMEME;
import com.apptechx.creatememe.Utils.Utils;
import com.apptechx.lib.Crop;
//import com.fourmob.colorpicker.ColorPickerDialog;
//import com.fourmob.colorpicker.ColorPickerSwatch;
import com.google.myjson.Gson;
import com.google.myjson.reflect.TypeToken;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by AppTechX on 10/21/2014.
 */
public class actCreateTemplate extends ActionBarActivity implements View.OnClickListener {

    private ImageView RESULT_VIEW;
    private Utils UTILS;
    private int IMAGE_ID;
    private List<String> LST_MYTEMPLATE, LST_MYIMAGE;
    private Button BTN_SAVE, BTN_ROTATE;
    private Uri outPutImage;
    private static final int SELECT_PICTURE = 1;
    private Bitmap bitmap, sourceBitmap;
    private EditText EDT_TOP, EDT_BOT;
    private Button BTN_CREATE, BTN_PREVIEW, BTN_SELECTTOPSIZE, BTN_SELECTBOTSIZE;
    private int SELECTED_COLOR = Color.WHITE;
    //    private ColorPickerDialog COLORPICKER_DIALOGE;
    private String IMAGE_PATH = "";
    private float TOPSIZE, BOTSIZE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_tamplate);
        RESULT_VIEW = (ImageView) findViewById(R.id.result_image);
        EDT_TOP = (EditText) findViewById(R.id.edtTop);
        EDT_BOT = (EditText) findViewById(R.id.edtBot);

        BTN_CREATE = (Button) findViewById(R.id.btnCreate);
        BTN_PREVIEW = (Button) findViewById(R.id.btnPreview);
        BTN_ROTATE = (Button) findViewById(R.id.btnRotateTemplate);
        BTN_SAVE = (Button) findViewById(R.id.btnCreateTemplate);
        BTN_SELECTTOPSIZE = (Button) findViewById(R.id.btnSelectTopSize);
        BTN_SELECTBOTSIZE = (Button) findViewById(R.id.btnSelectBotSize);
        BTN_CREATE.setOnClickListener(this);
        BTN_PREVIEW.setOnClickListener(this);
        BTN_ROTATE.setOnClickListener(this);
        BTN_SAVE.setOnClickListener(this);
        BTN_SELECTTOPSIZE.setOnClickListener(this);
        BTN_SELECTBOTSIZE.setOnClickListener(this);

        UTILS = new Utils(actCreateTemplate.this);
        IMAGE_ID = UTILS.getIntPreference(Constants.Extra.IMAGE_COUNT);

        TOPSIZE = getResources().getDimension(R.dimen.CodeFont);
        BOTSIZE = getResources().getDimension(R.dimen.CodeFont);

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

        // region GetMyImage
        String listMymeme = UTILS.getPreference(actCreateTemplate.this.getResources().getString(R.string.myImageLst));
        if (!listMymeme.equals("")) {
            Type collectionType = new TypeToken<Collection<String>>() {
            }.getType();
            LST_MYIMAGE = new Gson().fromJson(listMymeme,
                    collectionType);
        } else {
            LST_MYIMAGE = new ArrayList<String>();
        }

        EDT_TOP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (bitmap != null) {
                        CreateMEME createMEME;
                        File path;
                        createMEME = CreateMEME.GetInstance();
                        path = createMEME.CreateMEME(actCreateTemplate.this, EDT_TOP.getText().toString().toUpperCase()
                                , TOPSIZE, EDT_BOT.getText().toString().toUpperCase(), BOTSIZE, true, sourceBitmap, IMAGE_ID, SELECTED_COLOR);
                        IMAGE_PATH = path.getAbsolutePath();
                        setPic(path, IMAGE_PATH);
                    }
                    InputMethodManager imm = (InputMethodManager) actCreateTemplate.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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
                    if (bitmap != null) {
                        CreateMEME createMEME;
                        File path;
                        createMEME = CreateMEME.GetInstance();
                        path = createMEME.CreateMEME(actCreateTemplate.this, EDT_TOP.getText().toString().toUpperCase()
                                , TOPSIZE, EDT_BOT.getText().toString().toUpperCase(), BOTSIZE, true, sourceBitmap, IMAGE_ID, SELECTED_COLOR);
                        IMAGE_PATH = path.getAbsolutePath();
                        setPic(path, IMAGE_PATH);
                    }
                    InputMethodManager imm = (InputMethodManager) actCreateTemplate.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        // endregion
        createActionBarMenu();
//        setColorPicker();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_template_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent _intent = new Intent(actCreateTemplate.this, actTemplateList.class);
            startActivity(_intent);
            finish();
        }
        if (item.getItemId() == R.id.action_chooseTemplate) {
            RESULT_VIEW.setImageDrawable(null);
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            return true;
        }
        if (item.getItemId() == R.id.action_cropTemplate) {
            RESULT_VIEW.setImageDrawable(null);
            Crop.pickImage(actCreateTemplate.this);
            return true;
        }
        if (item.getItemId() == R.id.action_ChooseColor) {
            colorpicker();
//            COLORPICKER_DIALOGE.show(actCreateTemplate.this.getSupportFragmentManager(), "colorpicker");
            return true;
        }
        if (item.getItemId() == R.id.action_ShareImage) {
            if (!IMAGE_PATH.equals("")) {
                Toast.makeText(actCreateTemplate.this.getApplicationContext(), "Option Selected..!!", Toast.LENGTH_SHORT).show();
                Uri screenshotUri = Uri.parse(IMAGE_PATH);
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));
            } else {
                Toast.makeText(actCreateTemplate.this.getApplicationContext(), "Please create meme first.", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent _intent = new Intent(actCreateTemplate.this, actTemplateList.class);
        startActivity(_intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = result.getData();
                outPutImage = result.getData();
                String selectedImagePath = getPath(selectedImageUri);
                bitmap = BitmapProcess.decodeSampledBitmapFromPah(selectedImagePath, 800, 800);
                sourceBitmap = BitmapProcess.decodeSampledBitmapFromPah(selectedImagePath, 800, 800);
                RESULT_VIEW.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onClick(View view) {
        CreateMEME createMEME = CreateMEME.GetInstance();
        File path = null;
        switch (view.getId()) {
            case R.id.btnCreate:
                if (bitmap != null) {
                    RESULT_VIEW.setImageDrawable(null);

                    IMAGE_ID = UTILS.getIntPreference(Constants.Extra.IMAGE_COUNT);
                    path = createMEME.CreateMEME(actCreateTemplate.this, EDT_TOP.getText().toString().toUpperCase(),
                            TOPSIZE, EDT_BOT.getText().toString().toUpperCase(), BOTSIZE, false, sourceBitmap, IMAGE_ID, SELECTED_COLOR);
                    IMAGE_PATH = path.getAbsolutePath();
                    setPic(path.getAbsolutePath());
                } else {
                    Toast.makeText(actCreateTemplate.this, "Please select image first..!!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnPreview:
                if (bitmap != null) {
                    RESULT_VIEW.setImageDrawable(null);
                    path = createMEME.CreateMEME(actCreateTemplate.this, EDT_TOP.getText().toString().toUpperCase()
                            , TOPSIZE, EDT_BOT.getText().toString().toUpperCase(), BOTSIZE, true, sourceBitmap, IMAGE_ID, SELECTED_COLOR);
                    IMAGE_PATH = path.getAbsolutePath();
                    setPic(path, IMAGE_PATH);
                } else {
                    Toast.makeText(actCreateTemplate.this, "Please select image first..!!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCreateTemplate:
                if (bitmap != null) {
                    File _createdImg = getOutputMediaFile(IMAGE_ID);
                    Savefile(outPutImage, _createdImg);
                    IMAGE_PATH = _createdImg.getAbsolutePath();
                    LST_MYTEMPLATE.add(_createdImg.getAbsolutePath());
                    String listMyTemplate = new Gson().toJson(LST_MYTEMPLATE);
                    UTILS.setPreference(getResources().getString(R.string.myTempLateLst), listMyTemplate);
                    IMAGE_ID = IMAGE_ID + 1;
                    UTILS.setIntPrefrences(Constants.Extra.IMAGE_COUNT, IMAGE_ID);
                    Toast.makeText(getApplicationContext(), "Template Created SuccessFully", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnRotateTemplate:
                if (sourceBitmap != null) {
                    sourceBitmap = RotateBitmap(sourceBitmap, 90);
                    RESULT_VIEW.setImageBitmap(sourceBitmap);
                }
                break;
            case R.id.btnSelectTopSize:
                new DialogFontSize(actCreateTemplate.this, "TOP").show();
                break;
            case R.id.btnSelectBotSize:
                new DialogFontSize(actCreateTemplate.this, "BOT").show();
                break;
        }
    }

    private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(getCacheDir(), "cropped.jpg"));
        new Crop(source).output(outputUri).asSquare().withMaxSize(800, 800).start(actCreateTemplate.this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            outPutImage = Crop.getOutput(result);
            Log.d("::::: ", outPutImage.getPath());
//            String selectedImagePath = getPath(outPutImage);
            bitmap = BitmapProcess.decodeSampledBitmapFromPah(outPutImage.getPath(), 800, 800);
            sourceBitmap = BitmapProcess.decodeSampledBitmapFromPah(outPutImage.getPath(), 800, 800);
            RESULT_VIEW.setImageBitmap(bitmap);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(actCreateTemplate.this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private File getOutputMediaFile(int image_id) {
        String root = Environment.getExternalStorageDirectory().toString();
        File mediaStorageDir = new File(root + "/Jo_Baka");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile;
        String mImageName = "template_" + image_id + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    void Savefile(Uri souUri, File desFile) {
//        String sourceFilename = SOURCE_IMAGE_PATH;//= souUri.getPath();
//
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//
//        try {
//            bis = new BufferedInputStream(new FileInputStream(sourceFilename));
//            bos = new BufferedOutputStream(new FileOutputStream(desFile.getPath(), false));
//            byte[] buf = new byte[1024];
//            bis.read(buf);
//            do {
//                bos.write(buf);
//            } while (bis.read(buf) != -1);
//        } catch (IOException e) {
//
//        } finally {
//            try {
//                if (bis != null) bis.close();
//                if (bos != null) bos.close();
//            } catch (IOException e) {
//
//            }
//        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(desFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void setPic(String PATH) {

        Bitmap bitmap = BitmapProcess.decodeSampledBitmapFromPah(PATH, 800, 800);
        RESULT_VIEW.setImageBitmap(bitmap);
        LST_MYIMAGE.add(PATH);
        String listMyImages = new Gson().toJson(LST_MYIMAGE);
        UTILS.setPreference(actCreateTemplate.this.getResources().getString(R.string.myImageLst), listMyImages);
        Toast.makeText(actCreateTemplate.this.getApplicationContext(), "Meme Created SuccessFully", Toast.LENGTH_SHORT).show();
        IMAGE_ID = IMAGE_ID + 1;
        UTILS.setIntPrefrences(Constants.Extra.IMAGE_COUNT, IMAGE_ID);
    }

    private void setPic(File imgFile, String PATH) {
        Bitmap bitmap = BitmapProcess.decodeSampledBitmapFromPah(PATH, 800, 800);
        RESULT_VIEW.setImageBitmap(bitmap);
//        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//        RESULT_VIEW.setImageBitmap(myBitmap);
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
////                Toast.makeText(actCreateTemplate.this, "selectedColor : " + color, Toast.LENGTH_SHORT).show();
//                SELECTED_COLOR = color;
//            }
//        });
//
//    }

    private void createActionBarMenu() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Custom Meme");
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    public void colorpicker() {
        //     initialColor is the initially-selected color to be shown in the rectangle on the left of the arrow.
        //     for example, 0xff000000 is black, 0xff0000ff is blue. Please be aware of the initial 0xff which is the alpha.

        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, SELECTED_COLOR, new OnAmbilWarnaListener() {

            // Executes, when user click Cancel button
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            // Executes, when user click OK button
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                SELECTED_COLOR = color;
//                Toast.makeText(getBaseContext(), "Selected Color : " + color, Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }

    public String getPath(Uri uri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);

        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}