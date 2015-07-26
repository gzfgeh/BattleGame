package com.gzfgeh.battlegame.ui.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gzfgeh.battlegame.R;
import com.gzfgeh.battlegame.socket.MinaManager;
import com.gzfgeh.battlegame.utils.CmdUtils;
import com.gzfgeh.battlegame.utils.IntentTypeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by guzhenf on 6/27/2015.
 */
public class MainDisplay extends BaseActivity implements View.OnClickListener {
    private int token;
    private String user;
    private String rooms;
    private TextView tvUser, tvNoRoom;
    private Button btnCreateRoom;
    private ListView listRooms;
    private JSONObject object;
    private List<String> roomList = new ArrayList<>();

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
        rooms = bundle.getString(IntentTypeUtils.ROOM_LIST);
    }

    private void initView(){
        tvUser  = (TextView) findViewById(R.id.tv_user);
        tvUser.setText(user);
        btnCreateRoom = (Button) findViewById(R.id.btn_create_room);
        btnCreateRoom.setOnClickListener(this);

        listRooms = (ListView) findViewById(R.id.list_view);
        tvNoRoom = (TextView) findViewById(R.id.no_room_text);

        showListView();
    }

    private void showListView(){
        if (rooms == null){
            tvNoRoom.setVisibility(View.VISIBLE);
            listRooms.setVisibility(View.GONE);
        }else{
            JSONArray array = parseArray(rooms);
            for(int i=0; i<array.size(); i++){
                object = array.getJSONObject(i);
                roomList.add(object.getString("name"));
            }
            tvNoRoom.setVisibility(View.GONE);
            listRooms.setVisibility(View.VISIBLE);
            listRooms.setAdapter(new SimpleAdapter(this, getDatas(),R.layout.room_item,
                    new String[]{"room_index"}, new int[]{R.id.room_num}));
        }
    }

    private List<Map<String, String>> getDatas(){
        List<Map<String, String>> datas = new ArrayList<>();
        for (int i=0; i<roomList.size(); i++){
            Map<String, String> data = new HashMap<>();
            data.put("room_index", roomList.get(i));
            datas.add(data);
        }
        return datas;
    }

    @Override
    public void onClick(View v) {
        object = new JSONObject();
        object.put("cmd", "room_create");
        MinaManager.sendMessage(this, CmdUtils.NetByte(object.toString()));
    }

    @Override
    public void onMessageReceived(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        String msg = message.substring(2);
        object = parseObject(msg);

        switch (object.getString("cmd")){
            case "room_create":
                String roomNum = object.getString("rid");
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.is_create)+roomNum)
                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                object = new JSONObject();
                                object.put("cmd", "room_list");
                                object.put("page_index", "1");
                                MinaManager.sendMessage(MainDisplay.this, CmdUtils.NetByte(object.toString()));
                            }
                        })
                        .setNegativeButton(R.string.Cancle, null)
                        .show();
                break;

            case "room_list":
                rooms = object.getString("room");
                showListView();
                break;
        }



    }
}
