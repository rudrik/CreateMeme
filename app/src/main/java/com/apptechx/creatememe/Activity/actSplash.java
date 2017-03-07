package com.apptechx.creatememe.Activity;


import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import com.apptechx.creatememe.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class actSplash extends ActionBarActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_page);

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                Intent intent = new Intent(actSplash.this, actTemplateList.class);
                startActivity(intent);
                finish();
            }
        }.start();
//        CreateImage();
    }

    public void CreateImage() {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.jo_baka);
//        Log.d("Bitmap Height ::: ", String.valueOf(bm.getHeight()));
//        Log.d("Bitmap Width ::: ", String.valueOf(bm.getWidth()));
        Bitmap alteredBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
        Canvas canvas = new Canvas(alteredBitmap);
        Paint paint = new Paint();
        canvas.drawBitmap(bm, 0, 0, paint);
        Typeface font = Typeface.createFromAsset(getAssets(), "black.otf");


        TextPaint textPaintTop = new TextPaint();
        textPaintTop.setTextSize(getResources().getDimension(R.dimen.CodeFont));
        textPaintTop.setTypeface(font);
        StaticLayout mTextLayoutTop = new StaticLayout("Jo Baka", textPaintTop, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.save();
        int xPosTop = 0;
//        Log.d("Canvas Height ::: ", String.valueOf(canvas.getHeight()));
//        Log.d("Canvas Width ::: ", String.valueOf(canvas.getWidth()));
        int yPosTop = (int) (canvas.getHeight() / 11);
//        Log.d("TEXT :::", String.valueOf(((textPaintTop.descent() + textPaintTop.ascent()) / 2)));
//        Log.d("Xpos ::: ", String.valueOf(xPosTop));
//        Log.d("Ypos ::: ", String.valueOf(yPosTop));

        canvas.translate(xPosTop, yPosTop);
        mTextLayoutTop.draw(canvas);
        canvas.restore();


        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(getResources().getDimension(R.dimen.CodeFont));
        textPaint.setTypeface(font);
        StaticLayout mTextLayout = new StaticLayout("aa to khalo testing mate j image che", textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.save();
        int xPos = 0;
        int yPos = (int) ((canvas.getHeight() / 1.5) - ((textPaint.descent() + textPaint.ascent()) / 2));
//        Log.d("TEXT :::", String.valueOf(((textPaint.descent() + textPaint.ascent()) / 2)));
//        Log.d("XposBOT ::: ", String.valueOf(xPos));
//        Log.d("YposBOT ::: ", String.valueOf(yPos));

        canvas.translate(xPos, yPos);
        mTextLayout.draw(canvas);
        canvas.restore();


//        paint.setColor(Color.BLACK);
//        paint.setTextSize(100);
//        paint.setTypeface(font);
//        canvas.drawText("Jo Baka", 400, 200, paint);
//        int xPos = (canvas.getWidth() / 2);
//        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
//        paint.setTextAlign(Paint.Align.CENTER);
//        canvas.drawText("Aa khali Testing j che dsad ad asda da da", 0, 1000, paint);
        storeImage(alteredBitmap);
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(":::: ",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(":::: ", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(":::: ", "Error accessing file: " + e.getMessage());
        }
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
        File mediaStorageDir = new File(root + "/Jo_Baka");
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
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


}
