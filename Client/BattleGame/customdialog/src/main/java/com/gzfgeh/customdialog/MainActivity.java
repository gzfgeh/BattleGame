package com.gzfgeh.customdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.gzfgeh.customdialog.effects.Effectstype;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.custom_dialog);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.custom_dialog:
                CustomDialog dialogBuilder=CustomDialog.getInstance(this);
                Effectstype effect= Effectstype.Fadein;

                dialogBuilder
                        .withMessage("dddd")                                 //.withTitle(null)  no title
                        .withTitleColor("#33CCFF")                                  //def
                        .withDividerColor("#33CCFF")
                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                        .withDuration(300)                                          //def
                        .withEffect(effect)                                        //.setCustomView(View or ResId,context)
                        .show();
                break;
        }
    }
}
