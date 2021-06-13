package com.CampusNavigation.Log;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.CampusNavigation.Gui.CampusMapActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class LOG {
        private static FileWriter fileWriter=null;
        public static void setLOG(Context context, String path){
            //File file=context.getExternalFilesDir(path);
            path=context.getExternalFilesDir(Environment.DIRECTORY_ALARMS).getAbsolutePath();
            File file=new File( path+"/CampusNavigationLog.txt");
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fileWriter = new FileWriter(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            d("start Now");
        }
        public static void d(String msg) {
            Log.d("LOG-D", msg);
            try {
                if(fileWriter!=null)fileWriter.write("LOG-D "+msg+"\n");//写入本地文件的方法
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static void u(String msg)  {
            Log.d("USER-D",msg);
            try {
                if(fileWriter!=null)fileWriter.write("USER-D "+msg);//写入本地文件的方法
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
