package com.gzfgeh.battlegame.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.gzfgeh.battlegame.R;
import com.gzfgeh.battlegame.socket.MinaManager;
import com.gzfgeh.battlegame.utils.CmdUtils;
import com.gzfgeh.battlegame.utils.IntentTypeUtils;

import static com.alibaba.fastjson.JSON.parseObject;

public class LoginActivity extends BaseActivity {
    private Button btn_login;
    private TextView tv_register;
    private EditText et_user, et_password;
    private boolean once = true;
    private int uid, roomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_user = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();
                try {
                    object.put("cmd","auth");
                    object.put("name", et_user.getText().toString().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String s = CmdUtils.NetByte(object.toString());
                MinaManager.sendMessage(LoginActivity.this, s);
            }
        });

        tv_register = (TextView) findViewById(R.id.btn_register);
    }

    @Override
    public void onMessageReceived(String message) {
        if (TextUtils.isEmpty(message))
            return;

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        String msg = message.substring(2);
        JSONObject object = parseObject(msg);

        if (once){
            String id = object.getString("uid");
            if (id != null && Integer.valueOf(id) > 0)
                uid = Integer.valueOf(id);

            object = new JSONObject();
            object.put("cmd", "room_list");
            object.put("page_index", "1");
            MinaManager.sendMessage(this, CmdUtils.NetByte(object.toString()));
            once = false;
        }else{
            String roomList = object.getString("room");
            if (roomList == null || TextUtils.isEmpty(roomList))
                return;
            else{
                if (TextUtils.equals(roomList, "[]"))
                    roomNum = 0;
                Intent intent = new Intent(this, MainDisplay.class);
                Bundle bundle = new Bundle();
                bundle.putInt(IntentTypeUtils.INTENT_KEY, uid);
                bundle.putInt(IntentTypeUtils.ROOM_NUM, roomNum);
                bundle.putString(IntentTypeUtils.USER_KEY, et_user.getText().toString().trim());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

        }
    }
}
