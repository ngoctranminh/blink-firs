package com.scancode;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.facebook.react.bridge.Callback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;

public class MyQRCode extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;
    private Callback successCallback = null;
    private Callback errorCallback  = null;
    MyQRCode (ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @ReactMethod
    public void myQRCodeEvent(String name, Callback success , Callback error) {
        try {
        successCallback = success;
        errorCallback = error;
        MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix matrix = writer.encode(name, BarcodeFormat.QR_CODE,
                    350,350);
            BarcodeEncoder encoder =  new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            String path = MediaStore.Images.Media.insertImage(reactContext.getContentResolver(), bitmap, "Title", null);
            successCallback.invoke( path);
        } catch (WriterException e) {
            errorCallback.invoke(e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public String getName() {
        return "MyQRCode";
    }
}
