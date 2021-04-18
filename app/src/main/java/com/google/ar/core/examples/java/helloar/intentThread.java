package com.google.ar.core.examples.java.helloar;

import android.content.Intent;
import android.util.Log;

public class intentThread extends Thread{
    Intent i;
    intentThread(Intent temp)
    {
        i = temp;
        Log.d("Path","Constructor created");
    }
    public void run()
    {
        Log.d("Path","Inside thread");
        try {
            HelloArActivity.asset_file_name = i.getStringExtra("file");
        }
        catch (Exception e)
        {
            Log.d("Path","file not found");
        }
    }
}
