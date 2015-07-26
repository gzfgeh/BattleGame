package com.gzfgeh.battlegame.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.gzfgeh.battlegame.R;
import com.gzfgeh.battlegame.utils.IntentTypeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guzhenf on 6/27/2015.
 */
public class MainDisplay extends BaseActivity{
    private int token;
    private String user;
    private int rooms;
    private TextView tvUser;
    private Button btnCreateRoom;
    private ListView listRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_room);

        parseIntent(getIntent());
        initView();
    }

    private void parseIntent(Intent intent){
        if (intent == null)
            return ;

        Bundle bundle = intent.getExtras();
        token = bundle.getInt(IntentTypeUtils.INTENT_KEY);
        user  = bundle.getString(IntentTypeUtils.USER_KEY);
        rooms = bundle.getInt(IntentTypeUtils.ROOM_NUM);
    }

    private void initView(){
        tvUser  = (TextView) findViewById(R.id.tv_user);
        btnCreateRoom = (Button) findViewById(R.id.btn_create_room);
        tvUser.setText(user);
        listRooms = (ListView) findViewById(R.id.list_view);
        listRooms.setAdapter(new SimpleAdapter(this, getDatas(),R.layout.room_item,
                new String[]{"room_index"}, new int[]{R.id.room_num}));
    }

    private List<Map<String, Integer>> getDatas(){
        List<Map<String, Integer>> datas = new ArrayList<>();
        for (int i=0; i<rooms; i++){
            Map<String, Integer> data = new HashMap<>();
            data.put("room_index", new Integer(i));
            datas.add(data);
        }
        return datas;
    }
}
