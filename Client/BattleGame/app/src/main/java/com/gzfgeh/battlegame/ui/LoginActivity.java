package com.gzfgeh.battlegame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gzfgeh.battlegame.R;
import com.gzfgeh.battlegame.socket.MinaManager;
import com.gzfgeh.battlegame.utils.EncryptUtils;

public class LoginActivity extends BaseActivity {
    private Button btn_login;
    private TextView tv_register;
    private EditText et_user, et_password;

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
                String message = et_user.getText().toString() + et_password.getText().toString();
                message = EncryptUtils.NetByte(message);
                //Message msg = new Message(message);
                MinaManager.sendMessage(LoginActivity.this, message);

            }
        });

        tv_register = (TextView) findViewById(R.id.btn_register);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMessageReceived(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainDisplay.class);
        startActivity(intent);
        finish();
    }
}
