package com.gzfgeh.battlegame.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gzfgeh.battlegame.R;
import com.gzfgeh.battlegame.socket.MinaManager;
import com.gzfgeh.battlegame.utils.CmdUtils;
import com.gzfgeh.battlegame.utils.GridViewDialog;
import com.gzfgeh.battlegame.utils.IntentTypeUtils;
import com.gzfgeh.battlegame.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by guzhenfu on 15/8/5.
 */
public class RoomActivity extends BaseActivity implements View.OnClickListener, GridViewDialog.GirdViewDialogItem {
    private ImageView host, guest;
    private Button btnBegin, btnBack;
    private TextView tvRoomNum;

    private String user;
    private int rid, uid;
    private String enterType;
    private String cardString;
    private List<Map<String, Object>> data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);

        parseIntent();
        initView();
    }

    private void initView() {
        host = (ImageView) findViewById(R.id.host);
        guest = (ImageView) findViewById(R.id.guest);
        btnBegin = (Button) findViewById(R.id.begin);
        btnBack = (Button) findViewById(R.id.back);
        tvRoomNum = (TextView) findViewById(R.id.room_num);
        tvRoomNum.setText(String.valueOf(rid));

        if ("room_enter".equals(enterType)){
            guest.setVisibility(View.VISIBLE);
            btnBegin.setEnabled(true);
        }else{
            guest.setVisibility(View.GONE);
            btnBegin.setEnabled(false);
        }


        btnBack.setOnClickListener(this);
        btnBegin.setOnClickListener(this);
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent != null){
            user = intent.getStringExtra(IntentTypeUtils.USER_KEY);
            rid = intent.getIntExtra(IntentTypeUtils.ROOM_NUM, -1);
            uid = intent.getIntExtra(IntentTypeUtils.USER_ID, -1);
            enterType = intent.getStringExtra(IntentTypeUtils.ENTER_TYPE);
        }
    }

    @Override
    public void onMessageReceived(String message) {
        super.onMessageReceived(message);
        LogUtils.i("TAG", message);
        JSONObject object = parseObject(message);
        if (TextUtils.equals("character_list", object.getString("cmd"))){
            String nameList = object.getString("character_list");
            data = getDatas(nameList);
            GridViewDialog dialog = new GridViewDialog(this, data);
            dialog.setItem(this);
            dialog.show();
        }else{
            if (!btnBegin.isEnabled()){
                guest.setVisibility(View.VISIBLE);
                btnBegin.setEnabled(true);
            }
        }
    }

    private List getDatas(String nameList){
        List list = new ArrayList();
        JSONArray array = parseArray(nameList);
        for(int i=0; i<array.size(); i++){
            Map<String, Object> map = new HashMap<>();
            String name = array.getJSONObject(i).getString("name");
            map.put("itemText", name);
            switch (name){
                case "zhangfei":
                    map.put("itemImage", R.drawable.head_zf);
                    break;

                case "guanyu":
                    map.put("itemImage", R.drawable.head_gy);
                    break;

                case "liubei":
                    map.put("itemImage", R.drawable.head_lb);
                    break;
            }

            list.add(map);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.begin:
                if (cardString != null){
                    Intent intent = new Intent(this, PlayActivity.class);
                    intent.putExtra("card", cardString);
                    startActivity(intent);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void ItemClickListener(int position) {
        HashMap map = (HashMap) data.get(position);
        cardString = (String) map.get("itemText");
    }
}
