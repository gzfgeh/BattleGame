package com.gzfgeh.battlegame.ui.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gzfgeh.battlegame.R;

import org.w3c.dom.Text;

/**
 * Created by guzhenfu on 15/8/5.
 */
public class PlayActivity extends BaseActivity implements View.OnClickListener {
    private Button backBtn;
    private TextView tvCardsNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        initView();
    }

    private void initView() {
        backBtn = (Button) findViewById(R.id.back);
        backBtn.setOnClickListener(this);
        tvCardsNum = (TextView) findViewById(R.id.cards_num);
        tvCardsNum.setText("4");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                 finish();
                break;
        }
    }
}
