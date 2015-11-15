package com.gzfgeh.customview.CustomTouchEvent;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gzfgeh.customview.R;
import com.gzfgeh.customview.Utils;

import java.util.ArrayList;

/**
 * Created by guzhenfu on 15/11/14.
 */
public class TouchEventActivity extends Activity {
    private HorizontalScrollViewExt horizontalScrollViewExt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);

        LayoutInflater inflater = getLayoutInflater();
        horizontalScrollViewExt = (HorizontalScrollViewExt)findViewById(R.id.horizontal_scroll_view);
        int screenWidth = Utils.getScreenMetrics(this).widthPixels;
        //int screenHeight = Utils.getScreenMetrics(this).heightPixels;
        for (int i=0; i<3; i++){
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.content_layout, horizontalScrollViewExt, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page" + i);
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            horizontalScrollViewExt.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(TouchEventActivity.this, "click item",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }


}
