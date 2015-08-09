package com.gzfgeh.battlegame.ui.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gzfgeh.battlegame.R;
import com.gzfgeh.battlegame.adapter.RoomListAdapter;
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
public class MainDisplay extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private int uid, rid;
    private String user;
    private String rooms;
    private TextView tvUser, tvNoRoom;
    private Button btnCreateRoom;
    private ListView listRooms;
    private JSONObject object;
    private List<Map<String, Object>> roomList = new ArrayList<>();

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
        uid = bundle.getInt(IntentTypeUtils.INTENT_KEY);
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
        listRooms.setOnItemClickListener(this);
    }

    private void showListView(){
        if (rooms == null){
            tvNoRoom.setVisibility(View.VISIBLE);
            listRooms.setVisibility(View.GONE);
        }else{
            tvNoRoom.setVisibility(View.GONE);
            listRooms.setVisibility(View.VISIBLE);
            listRooms.setAdapter(new RoomListAdapter(this, getDatas()));
        }
    }

    private List<Map<String, Object>> getDatas(){
        JSONArray array = parseArray(rooms);
        for(int i=0; i<array.size(); i++){
            Map<String, Object> map = new HashMap<>();
            map.put("name", array.getJSONObject(i).getString("name"));
            map.put("number", array.getJSONObject(i).getString("rid"));
            roomList.add(map);
        }
        return roomList;
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
                Intent intent = new Intent(MainDisplay.this, RoomActivity.class);
                intent.putExtra(IntentTypeUtils.USER_KEY, user);
                intent.putExtra(IntentTypeUtils.USER_ID, uid);
                intent.putExtra(IntentTypeUtils.ROOM_NUM, rid);
                intent.putExtra(IntentTypeUtils.ENTER_TYPE, "room_create");
                startActivity(intent);
                break;

            case "room_list":
                rooms = object.getString("room");
                showListView();
                break;

            case "room_enter":
                Intent i = new Intent(MainDisplay.this, RoomActivity.class);
                i.putExtra(IntentTypeUtils.ENTER_TYPE, "room_enter");
                i.putExtra(IntentTypeUtils.USER_KEY, user);
                i.putExtra(IntentTypeUtils.USER_ID, uid);
                i.putExtra(IntentTypeUtils.ROOM_NUM, rid);
                startActivity(i);
                break;
        }



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String num = (String)roomList.get(position).get("number");
        rid = Integer.valueOf(num);

        JSONObject object = new JSONObject();
        object.put("cmd", "room_enter");
        object.put("uid", String.valueOf(uid));
        object.put("rid", rid);
        MinaManager.sendMessage(MainDisplay.this, CmdUtils.NetByte(object.toString()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        showListView();
    }
}
