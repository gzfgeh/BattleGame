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
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.gzfgeh.customdialog.effects.Effectstype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Button btn1, btn2, btn3, btn4, btn5, btn6;
    private ListView listView;
    private ListAdapter adapter;
    private CustomDialog dialogBuilder;
    private HashMap<Integer,Boolean> checkedMap = new HashMap<Integer, Boolean>();;
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
        btn3 = (Button) findViewById(R.id.list_view_image_dialog);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.list_view_checked_text);
        btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.list_view_checked_multext);
        btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.list_view_custom_checked);
        btn6.setOnClickListener(this);
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
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strs);
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
                break;

            case R.id.list_view_image_dialog:
                listView = new ListView(this);
                adapter = new SimpleAdapter(this, getData(), R.layout.list_view_image_item,
                        new String[]{"image", "text"},
                        new int[]{R.id.image, R.id.text});
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
                break;

            case R.id.list_view_checked_text:
                listView = new ListView(this);
                listView.setItemsCanFocus(false);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, strs);
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
                break;

            case R.id.list_view_checked_multext:
                listView = new ListView(this);
                listView.setItemsCanFocus(false);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, strs);
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
                break;

            case R.id.list_view_custom_checked:
                listView = new ListView(this);
                listView.setItemsCanFocus(false);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                adapter = new CheckedTextAdapter(this, getData(), checkedMap);
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
                break;

        }
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<>();
        map.put("text", "G1");
        map.put("image", R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<>();
        map.put("text", "G2");
        map.put("image", R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<>();
        map.put("text", "G3");
        map.put("image", R.mipmap.ic_launcher);
        list.add(map);

        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
        if (checkedMap != null){
            CheckedTextView multiple = (CheckedTextView) view.findViewById(R.id.checktv_title);
            if(multiple.isChecked()){
                multiple.setChecked(false);
                checkedMap.put(position, false);

            }else{
                multiple.setChecked(true);
                checkedMap.put(position,true);
            }
        }
        //dialogBuilder.dismiss();
    }

}
