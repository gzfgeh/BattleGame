package com.gzfgeh.battlegame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gzfgeh.battlegame.R;
import com.gzfgeh.battlegame.View.CustomProgress;
import com.gzfgeh.battlegame.View.TopBar;
import com.gzfgeh.battlegame.socket.MinaManager;
import com.gzfgeh.battlegame.utils.EncryptUtils;
import com.gzfgeh.battlegame.utils.SHA1Utils;
import com.gzfgeh.battlegame.utils.ShowViewUtils;

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
    private TopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        topBar = (TopBar) findViewById(R.id.top_bar);
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

                JSONObject object = new JSONObject();
                try {
                    object.put("cmd", "register");
                    object.put("user", name);
                    object.put("pwd", new SHA1Utils().getDigestOfString(pwd.getBytes()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String message = EncryptUtils.NetByte(object.toString());
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                MinaManager.sendMessage(RegisterActivity.this, message);
                CustomProgress.show(RegisterActivity.this, "加载中... ", true, null);
            }
        });

        topBar.setListener(new TopBar.OnLeftAndRightClickListener() {
            @Override
            public void onLeftButtonClick() {
                finish();
            }

            @Override
            public void onRightButtonClick() {
                Toast.makeText(RegisterActivity.this, "rightBtn", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onMessageReceived(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        CustomProgress.cancle();
        new ShowViewUtils(this).hideProgressDialog();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
