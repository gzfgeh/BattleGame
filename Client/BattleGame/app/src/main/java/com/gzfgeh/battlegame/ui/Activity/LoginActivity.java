package com.gzfgeh.battlegame.ui.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gzfgeh.battlegame.R;
import com.gzfgeh.battlegame.socket.MinaManager;
import com.gzfgeh.battlegame.utils.EncryptUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {
    private Button btn_login;
    private TextView tv_register;
    private EditText et_user, et_password;
    private boolean once = true;
    private String uid;

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
                String s = EncryptUtils.NetByte(object.toString());
                MinaManager.sendMessage(LoginActivity.this, s);
            }
        });

        tv_register = (TextView) findViewById(R.id.btn_register);
    }

    @Override
    public void onMessageReceived(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        String msg = message.substring(2);
        try {
            JSONObject object = new JSONObject(msg);
            String auth = object.getString("uid");
            String cmd = object.getString("cmd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        if (once){
//            uid = message;
//            JSONObject object = new JSONObject();
//            try {
//                object.put("cmd", "room_list");
//                object.put("page_index", "1");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            MinaManager.sendMessage(LoginActivity.this, EncryptUtils.NetByte(object.toString()));
//            once = false;
//        }else {
//            Intent intent = new Intent(this, MainDisplay.class);
//            intent.putExtra(IntentTypeUtils.INTENT_KEY, uid);
//            intent.putExtra(IntentTypeUtils.ROOM_NUM, message);
//            intent.putExtra(IntentTypeUtils.USER_KEY, et_user.getText().toString().trim());
//            //startActivity(intent);
//            //finish();
//        }
    }
}
