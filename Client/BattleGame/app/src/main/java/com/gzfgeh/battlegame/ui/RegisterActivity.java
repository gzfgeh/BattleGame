package com.gzfgeh.battlegame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gzfgeh.battlegame.R;
import com.gzfgeh.battlegame.socket.MinaManager;
import com.gzfgeh.battlegame.utils.EncryptUtils;
import com.gzfgeh.battlegame.utils.SHA1Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by guzhenf on 6/27/2015.
 */
public class RegisterActivity extends BaseActivity {

    private EditText userName;
    private EditText userPwd;
    private EditText userSecondPwd;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        userName = (EditText) findViewById(R.id.et_username);
        userPwd = (EditText) findViewById(R.id.et_password);
        userSecondPwd = (EditText) findViewById(R.id.et_email);

        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString().trim();
                String pwd = userPwd.getText().toString().trim();
                String secondPwd = userSecondPwd.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(secondPwd))
                    return ;

                if (!TextUtils.equals(pwd, secondPwd))
                    return;

                JSONArray array = new JSONArray();
                JSONObject object = new JSONObject();
                try {
                    //JSONObject object1 = new JSONObject();
                    //JSONObject object2 = new JSONObject();
                    //JSONObject object3 = new JSONObject();
                    //object.put("cmd", "register");
                    //object.put("user", name);
                    //object2.put("cmd", "register");
                    object.put("pwd", new SHA1Utils().getDigestOfString(pwd.getBytes()));
                    object.put("cmd", "register");
                    object.put("user", name);
                    array.put(object);
                    //array.put(object2);
                    //array.put(object3);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), array.toString(), Toast.LENGTH_SHORT).show();
                MinaManager.sendMessage(RegisterActivity.this, array.toString());
            }
        });
    }

    @Override
    public void onMessageReceived(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
