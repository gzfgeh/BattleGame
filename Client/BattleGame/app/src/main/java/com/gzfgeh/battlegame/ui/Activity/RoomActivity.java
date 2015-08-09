package com.gzfgeh.battlegame.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gzfgeh.battlegame.R;
import com.gzfgeh.battlegame.utils.IntentTypeUtils;

/**
 * Created by guzhenfu on 15/8/5.
 */
public class RoomActivity extends BaseActivity implements View.OnClickListener {
    private ImageView host, guest;
    private Button btnBegin, btnBack;
    private TextView tvRoomNum;

    private String user;
    private int rid, uid;
    private String enterType;

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

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        guest.setVisibility(View.VISIBLE);
        btnBegin.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.begin:
                Intent intent = new Intent(this, PlayActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }
}
