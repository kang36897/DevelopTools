package com.curious.tiger.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;

import com.curious.support.logger.Log;
import com.curious.tiger.data.MImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lulala on 9/4/16.
 */
public class ImageUtils {
    final static String TAG = "ImageUtils";

    public static void saveImage(byte[] data, File image) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(image);
            outputStream.write(data);
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "saveImage()->", e);
        } catch (IOException e) {
            Log.e(TAG, "saveImage()->", e);
        }
    }

    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            android.util.Log.e(TAG, "cannot read exif", ex);
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }
        return degree;
    }

    public static void clipImage(byte[] data, Point[] sensitiveAreas, MImage mImage) {
        File sourceFile = StorageUtils.getOutputMediaFile(StorageUtils.MEDIA_TYPE_IMAGE);

        if (sourceFile == null) {
            Log.e("TAG", "clipImage()-> can not get a path to save images");
            return;
        }

        saveImage(data, sourceFile);

        int rotateDegree = getExifOrientation(sourceFile.getAbsolutePath());

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        int imgWidth = opts.outWidth;
        int imgHeight = opts.outHeight;

        opts.inJustDecodeBounds = false;
        Bitmap source = BitmapFactory.decodeByteArray(data, 0, data.length, opts);

        int x, y, wd, wh;
        float wRate = (float) sensitiveAreas[1].x / sensitiveAreas[0].x;
        float hRate = (float) sensitiveAreas[1].y / sensitiveAreas[0].y;

        if (imgWidth > imgHeight) {
            wd = (int) (hRate * imgWidth);
            wh = (int) (wRate * imgHeight);
        } else {
            wd = (int) (wRate * imgWidth);
            wh = (int) (hRate * imgHeight);
        }

        x = (imgWidth - wd) / 2;
        y = (imgHeight - wh) / 2;

        Matrix m = new Matrix();
        m.setRotate(rotateDegree);
        Bitmap clippedOne = Bitmap.createBitmap(source, x, y, wd, wh, m, false);
        source.recycle();


        int maxLength = 1152;
        clippedOne = scaleImage(clippedOne, maxLength);

        Log.d(TAG, "img width = " + clippedOne.getWidth() + ", height = " + clippedOne.getHeight());
        saveImage(clippedOne, sourceFile);
        Log.d(TAG, "img file size = " + sourceFile.length() / 1024 + "kb");
        if (mImage == null) {
            clippedOne.recycle();
        } else {
            mImage.mBitmap = clippedOne;
            mImage.mImgPath = sourceFile.getAbsolutePath();
        }

    }

    private static Bitmap scaleImage(Bitmap sourceImage, int maxLength) {

        int wd = sourceImage.getWidth();
        int wh = sourceImage.getHeight();

        if (wd > wh) {
            wh = maxLength * wh / wd;
            wd = maxLength;
        } else {
            wd = maxLength * wd / wh;
            wh = maxLength;
        }

        Bitmap temp = Bitmap.createScaledBitmap(sourceImage, wd, wh, false);
        sourceImage.recycle();

        return temp;
    }

    public static void saveImage(Bitmap clippedOne, File sourceFile) {
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(sourceFile);
            clippedOne.compress(Bitmap.CompressFormat.JPEG, 40, outputStream);
            outputStream.close();

        } catch (FileNotFoundException e) {
            Log.e(TAG, "saveImage()->", e);
        } catch (IOException e) {
            Log.e(TAG, "saveImage()->", e);
        }
    }


    public static void clipImage(byte[] data, Point[] sensitiveAreas, int rotateDegree, MImage mImage) {
        File image;
        FileOutputStream outputStream;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        int imgWidth = opts.outWidth;
        int imgHeight = opts.outHeight;

        opts.inJustDecodeBounds = false;
        Bitmap source = BitmapFactory.decodeByteArray(data, 0, data.length, opts);

        int x, y, wd, wh;
        float wRate = (float) sensitiveAreas[1].x / sensitiveAreas[0].x;
        float hRate = (float) sensitiveAreas[1].y / sensitiveAreas[0].y;

        if (imgWidth > imgHeight) {
            wd = (int) (hRate * imgWidth);
            wh = (int) (wRate * imgHeight);
        } else {
            wd = (int) (wRate * imgWidth);
            wh = (int) (hRate * imgHeight);
        }

        x = (imgWidth - wd) / 2;
        y = (imgHeight - wh) / 2;

        Matrix m = new Matrix();
        m.setRotate(rotateDegree);
        Bitmap clippedOne = Bitmap.createBitmap(source, x, y, wd, wh, m, false);
        source.recycle();

        image = StorageUtils.getOutputMediaFile(StorageUtils.MEDIA_TYPE_IMAGE);
        saveImage(clippedOne, image);
        if (mImage == null) {
            clippedOne.recycle();
        } else {
            mImage.mBitmap = clippedOne;
            mImage.mImgPath = image.getAbsolutePath();
        }


    }

    public static int captureScreenByCmd(File imgFile) {

        Process process = null;
        try {
            process = Runtime.getRuntime().exec("/system/bin/screencap -p " + imgFile.getAbsolutePath());

            InputStream inputStream = process.getInputStream();
            saveImage(inputStream, imgFile);


            final InputStream errorStream = process.getErrorStream();
            new Thread() {
                @Override
                public void run() {
                    CmdUtils.logError(errorStream);
                }
            }.start();

            return process.waitFor();

        } catch (IOException e) {
            Log.e(TAG, "captureScreen()->", e);
        } catch (InterruptedException e) {
            Log.e(TAG, "captureScreen()->", e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

        return -1;
    }

    public static void saveImage(InputStream inputStream, File imgFile) {
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int count = 0;
            while ((count = inputStream.read(buff)) != -1) {
                outputStream.write(buff);
            }
            outputStream.flush();
            saveImage(outputStream.toByteArray(), imgFile);
            outputStream.close();

        } catch (FileNotFoundException e) {
            Log.e(TAG, "saveImage()->", e);
        } catch (IOException e) {
            Log.e(TAG, "saveImage()->", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "saveImage()->", e);
                }
            }
        }
    }


}
