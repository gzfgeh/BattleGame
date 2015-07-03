package com.gzfgeh.battlegame.utils;

import android.content.Context;
import android.widget.Toast;

import com.gzfgeh.battlegame.View.CustomProgress;

/**
 * Created by guzhenf on 7/2/2015.
 */
public class ShowViewUtils {

    private Toast toast;
    private Context context;

    public ShowViewUtils(Context context){
        this.context = context;
    }

    public static void showProgressDialog(Context context, String message) {
        CustomProgress.show(context, message, true, null);
    }

    public void hideProgressDialog() {

    }

    public void showToast(String hint){
        if (toast != null){
            toast=Toast.makeText(context,hint,Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void hideToast(){
        if (toast != null)
            toast.cancel();
    }
}
