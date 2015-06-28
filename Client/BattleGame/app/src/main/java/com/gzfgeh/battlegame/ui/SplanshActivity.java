package com.gzfgeh.battlegame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.gzfgeh.battlegame.socket.MinaManager;

import battlegame.battlegame.R;

/**
 * Created by guzhenf on 6/28/2015.
 */
public class SplanshActivity extends BaseActivity {
    private EditText et_ip;
    private EditText et_port;
    private Button bt_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(this, R.layout.activity_splansh, null);
        setContentView(view);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(2000);
        view.startAnimation(alphaAnimation);
        setSocket();
    }

    private void setSocket(){
        et_ip = (EditText) findViewById(R.id.ip);
        et_port = (EditText) findViewById(R.id.port);
        bt_connect = (Button) findViewById(R.id.set_socket);

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinaManager.init(SplanshActivity.this, et_ip.getText().toString(),
                        Integer.valueOf(et_port.getText().toString()));
            }
        });
    }

    @Override
    public void onConnectionSucceed() {
        Intent intent = new Intent(SplanshActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
