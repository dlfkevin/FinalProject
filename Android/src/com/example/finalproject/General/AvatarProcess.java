package com.example.finalproject.General;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

public class AvatarProcess {
	private static String filePath;
	
	/**
     * 
     * @param imgPath
     * @param bitmap
     * @param imgFormat Í¼Æ¬¸ñÊ½
     * @return
     */
    public static String SDcardImgToBase64(String imgPath) {
        Bitmap bitmap = null;
    	if (imgPath !=null && imgPath.length() > 0) {
            bitmap = readBitmap(imgPath);
            if(bitmap == null){
                return null;
            }else {
            	ByteArrayOutputStream out = null;
                try {
                    out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
         
                    out.flush();
                    out.close();
         
                    byte[] imgBytes = out.toByteArray();
                    return Base64.encodeToString(imgBytes, Base64.DEFAULT);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    return null;
                } finally {
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
			}
        }else {
			return null;
		}
    }
    
    public static String BitmapToBase64(Bitmap bitmap) {
        String imgPath = null;
    	if(bitmap == null){
            //bitmap not found!!
        	return null;
        }else {
        	ByteArrayOutputStream out = null;
            try {
                out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
     
                out.flush();
                out.close();
     
                byte[] imgBytes = out.toByteArray();
                imgPath = Base64.encodeToString(imgBytes, Base64.DEFAULT);
                //SaveAvatarToSDCard(imgPath);
                return imgPath;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                return null;
            } finally {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
		}
    }
    
    public static Bitmap Base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }
 
    private static Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }
 
    }
    
    private static void SaveAvatarToSDCard(String base64Data){
    	File sdPath = Environment.getExternalStorageDirectory();
    	filePath = sdPath.getPath() + "/Whatseat";
    }
}
