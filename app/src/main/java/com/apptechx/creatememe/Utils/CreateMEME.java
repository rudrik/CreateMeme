package com.apptechx.creatememe.Utils;


import android.content.Context;
import android.graphics.*;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by AppTechX on 10/15/2014.
 */
public class CreateMEME {

    private static CreateMEME objCreateMeme;

//    private CreateMEME() {}

    public static CreateMEME GetInstance() {
        if (objCreateMeme == null) {
            objCreateMeme = new CreateMEME();
        }
        return objCreateMeme;
    }

    public File CreateMEME(Context cxt, String top, float topSize, String bot, float botSize, boolean isPreview, int ImageID, String memeName,
                           int image_id, int color, Bitmap bitmapImage) {
        Bitmap bm;
        if (ImageID != 0) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            bm = BitmapFactory.decodeResource(cxt.getResources(), ImageID, options);
        } else {
            bm = bitmapImage;
        }


//      Log.d("Bitmap Height ::: ", String.valueOf(bm.getHeight()));
//      Log.d("Bitmap Width ::: ", String.valueOf(bm.getWidth()));
//      Bitmap alteredBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.RGB_565);

        Bitmap workingBitmap = Bitmap.createBitmap(bm);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
//        Canvas testcanvas = new Canvas(mutableBitmap);

        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        canvas.drawBitmap(bm, 0, 0, paint);
        Typeface font = Typeface.createFromAsset(cxt.getAssets(), "black.otf");

        TextPaint textPaintTop = new TextPaint();
//        float i = cxt.getResources().getDimension(R.dimen.CodeFont);
//        System.out.println(i);
        textPaintTop.setTextSize(topSize);
        textPaintTop.setTypeface(font);
        textPaintTop.setColor(color);
        StaticLayout mTextLayoutTop = new StaticLayout(top, textPaintTop, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.save();
        int xPosTop = 0;
//        Log.d("Canvas Height ::: ", String.valueOf(canvas.getHeight()));
//        Log.d("Canvas Width ::: ", String.valueOf(canvas.getWidth()));
        int yPosTop = 10;
//        Log.d("TEXT :::", String.valueOf(((textPaintTop.descent() + textPaintTop.ascent()) / 2)));
//        Log.d("Xpos ::: ", String.valueOf(xPosTop));
//        Log.d("Ypos ::: ", String.valueOf(yPosTop));

        canvas.translate(xPosTop, yPosTop);
        mTextLayoutTop.draw(canvas);
        canvas.restore();

        TextPaint textPaintBot = new TextPaint();
        textPaintBot.setTextSize(botSize);
        textPaintBot.setTypeface(font);
        textPaintBot.setColor(color);
        StaticLayout mTextLayout = new StaticLayout(bot, textPaintBot, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.save();
        int xPos = 0;
        int yPos = (int) ((canvas.getHeight() / 1.4) - ((textPaintBot.descent() + textPaintBot.ascent()) / 2));
//        Log.d("TEXT :::", String.valueOf(((textPaint.descent() + textPaint.ascent()) / 2)));
//        Log.d("XposBOT ::: ", String.valueOf(xPos));
//        Log.d("YposBOT ::: ", String.valueOf(yPos));
        yPos = canvas.getHeight() - mTextLayout.getHeight() - 10;
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


//        bm.recycle();
//        workingBitmap.recycle();

        return storeImage(mutableBitmap, isPreview, cxt, memeName, image_id);
    }

    public File CreateMEME(Context cxt, String top, float topSize, String bot, float botSize, boolean isPreview, Bitmap image,
                           int image_id, int color) {
//        Log.d("Bitmap Height ::: ", String.valueOf(bm.getHeight()));
//        Log.d("Bitmap Width ::: ", String.valueOf(bm.getWidth()));
//        Bitmap alteredBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.RGB_565);

        Bitmap workingBitmap = Bitmap.createBitmap(image);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
//        Canvas testcanvas = new Canvas(mutableBitmap);

        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        canvas.drawBitmap(image, 0, 0, paint);
        Typeface font = Typeface.createFromAsset(cxt.getAssets(), "black.otf");

        final float scale = cxt.getApplicationContext().getResources().getDisplayMetrics().density;
        int _topsize = (int) (topSize * scale + 0.5f);

        TextPaint textPaintTop = new TextPaint();
        textPaintTop.setTextSize(_topsize);
        textPaintTop.setTypeface(font);
        textPaintTop.setColor(color);
        StaticLayout mTextLayoutTop = new StaticLayout(top, textPaintTop, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.save();
        int xPosTop = 0;
//        Log.d("Canvas Height ::: ", String.valueOf(canvas.getHeight()));
//        Log.d("Canvas Width ::: ", String.valueOf(canvas.getWidth()));
        int yPosTop = 10;
//        Log.d("TEXT :::", String.valueOf(((textPaintTop.descent() + textPaintTop.ascent()) / 2)));
//        Log.d("Xpos ::: ", String.valueOf(xPosTop));
//        Log.d("Ypos ::: ", String.valueOf(yPosTop));
        canvas.translate(xPosTop, yPosTop);
        mTextLayoutTop.draw(canvas);
        canvas.restore();

        int _botsize = (int) (botSize * scale + 0.5f);


        TextPaint textPaintBot = new TextPaint();
        textPaintBot.setTextSize(_botsize);
        textPaintBot.setTypeface(font);
        textPaintBot.setColor(color);
        StaticLayout mTextLayout = new StaticLayout(bot, textPaintBot, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.save();
        int xPos = 0;
        int yPos = (int) ((canvas.getHeight() / 1.4) - ((textPaintBot.descent() + textPaintBot.ascent()) / 2));
//        Log.d("TEXT :::", String.valueOf(((textPaint.descent() + textPaint.ascent()) / 2)));
//        Log.d("XposBOT ::: ", String.valueOf(xPos));
//        Log.d("YposBOT ::: ", String.valueOf(yPos));
        yPos = canvas.getHeight() - mTextLayout.getHeight() - 10;
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
//        workingBitmap.recycle();

        return storeImage(mutableBitmap, isPreview, cxt, "memeName", image_id);
    }

    private File storeImage(Bitmap image, boolean isPreview, Context cxt, String memeName, int image_id) {
        File pictureFile = null;
        if (!isPreview) {
            pictureFile = getOutputMediaFile(memeName, image_id);
        } else {
            pictureFile = CreateTempImage(cxt);
        }
        if (pictureFile == null) {
            Log.d(":::: ",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.close();
            fos.flush();

        } catch (FileNotFoundException e) {
            Log.d(":::: ", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(":::: ", "Error accessing file: " + e.getMessage());
        }

        return pictureFile;
    }

    //    ** Create a File for saving an image or video */
    private File getOutputMediaFile(String memeName, int image_id) {
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
//        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "meme_" + image_id + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private File CreateTempImage(Context context) {
        File outputDir = context.getCacheDir(); // context being the Activity pointer
        File outputFile = null;
        try {
            outputFile = File.createTempFile("TEMP", ".jpg", outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }


}
