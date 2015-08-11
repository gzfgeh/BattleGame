package com.gzfgeh.battlegame.ui.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.gzfgeh.battlegame.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by guzhenfu on 15/8/5.
 */
public class PlayActivity extends BaseActivity implements View.OnClickListener {
    private Button backBtn;
    private TextView tvCardsNum;
    private String card;
    private ImageView card1, card2, card3, card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        initView();
        parseIntent();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void parseIntent() {
        Intent intent = getIntent();
        if (intent != null)
            card = intent.getStringExtra("card");

        JSONArray array = parseArray(card);
        for(int i=0; i<array.size(); i++){
             String cardType = array.getJSONObject(i).getString("name");
            switch (cardType){
                case "kill":
                    if (Bitmap.createBitmap(card1.getDrawingCache()) != null)
                        card1.setImageDrawable(getResources().getDrawable(R.drawable.kill));
                    else if (Bitmap.createBitmap(card2.getDrawingCache()) != null)
                        card2.setImageDrawable(getResources().getDrawable(R.drawable.kill));
                    else if (Bitmap.createBitmap(card3.getDrawingCache()) != null)
                        card3.setImageDrawable(getResources().getDrawable(R.drawable.kill));
                    else
                        card4.setImageDrawable(getResources().getDrawable(R.drawable.kill));
                    break;

                case "run":
                    if (Bitmap.createBitmap(card1.getDrawingCache()) != null)
                        card1.setImageDrawable(getResources().getDrawable(R.drawable.avoid));
                    else if (Bitmap.createBitmap(card2.getDrawingCache()) != null)
                        card2.setImageDrawable(getResources().getDrawable(R.drawable.avoid));
                    else if (Bitmap.createBitmap(card3.getDrawingCache()) != null)
                        card3.setImageDrawable(getResources().getDrawable(R.drawable.avoid));
                    else
                        card4.setImageDrawable(getResources().getDrawable(R.drawable.avoid));
                    break;

                case "peach":
                    if (Bitmap.createBitmap(card1.getDrawingCache()) != null)
                        card1.setImageDrawable(getResources().getDrawable(R.drawable.peach));
                    else if (Bitmap.createBitmap(card2.getDrawingCache()) != null)
                        card2.setImageDrawable(getResources().getDrawable(R.drawable.peach));
                    else if (Bitmap.createBitmap(card3.getDrawingCache()) != null)
                        card3.setImageDrawable(getResources().getDrawable(R.drawable.peach));
                    else
                        card4.setImageDrawable(getResources().getDrawable(R.drawable.peach));
                    break;
            }

        }


    }

    private void initView() {
        backBtn = (Button) findViewById(R.id.back);
        backBtn.setOnClickListener(this);
        tvCardsNum = (TextView) findViewById(R.id.cards_num);
        tvCardsNum.setText("4");
        card1 = (ImageView) findViewById(R.id.card1);
        card2 = (ImageView) findViewById(R.id.card2);
        card3 = (ImageView) findViewById(R.id.card3);
        card4 = (ImageView) findViewById(R.id.card4);
        card1.setDrawingCacheEnabled(true);
        card2.setDrawingCacheEnabled(true);
        card3.setDrawingCacheEnabled(true);
        card4.setDrawingCacheEnabled(true);
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
