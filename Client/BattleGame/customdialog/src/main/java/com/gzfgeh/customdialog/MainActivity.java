package com.gzfgeh.customdialog;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gzfgeh.customdialog.effects.Effectstype;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Button btn1, btn2;
    private ListView listView;
    private CustomDialog dialogBuilder;
    private static final String[] strs = new String[] {
        "first", "second", "third", "fourth", "fifth",
            "first", "second", "third", "fourth", "fifth",
            "first", "second", "third", "fourth", "fifth"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.normal_dialog);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.list_view_normal_dialog);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.normal_dialog:
                dialogBuilder=CustomDialog.getInstance(this);
                dialogBuilder
                        .withTitle("hello")
                        .withMessage("dddd")                                 //.withTitle(null)  no title
                        .withTitleColor("#33CCFF")                                  //def
                        .withDividerColor("#33CCFF")
                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                        .withDuration(300)                                    //.setCustomView(View or ResId,context)
                        .show();
                break;

            case R.id.list_view_normal_dialog:
                listView = new ListView(this);
                ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strs);
                listView.setAdapter(adapter);

                dialogBuilder=CustomDialog.getInstance(this);
                dialogBuilder
                        .withMessage(null)
                        .withTitle("hello")                                //.withTitle(null)  no title
                        .withTitleColor("#33CCFF")                                  //def
                        .withDividerColor("#33CCFF")
                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                        .withDuration(300)
                        .setCustomView(listView, this)
                        .show();
                listView.setOnItemClickListener(this);
                setListViewHeightBasedOnChildren(listView);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
        dialogBuilder.dismiss();
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        if (listAdapter.getCount() < 4)
            return;

        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = height/3;
        listView.setLayoutParams(params);
    }
}
