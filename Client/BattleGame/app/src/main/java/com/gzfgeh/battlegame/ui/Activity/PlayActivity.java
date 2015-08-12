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
    private boolean setCard1 = false, setCard2 = false, setCard3 = false, setCard4 = false;

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
                    if (!setCard1){
                        card1.setImageResource(R.drawable.kill);
                        setCard1 = true;
                    }else if (!setCard2){
                        card2.setImageResource(R.drawable.kill);
                        setCard2 = true;
                    }else if (!setCard3){
                        card3.setImageResource(R.drawable.kill);
                        setCard3 = true;
                    }else{
                        card4.setImageResource(R.drawable.kill);
                        setCard4 = true;
                    }
                    break;

                case "run":
                    if (!setCard1){
                        card1.setImageResource(R.drawable.avoid);
                        setCard1 = true;
                    }else if (!setCard2){
                        card2.setImageResource(R.drawable.avoid);
                        setCard2 = true;
                    }else if (!setCard3){
                        card3.setImageResource(R.drawable.avoid);
                        setCard3 = true;
                    }else{
                        card4.setImageResource(R.drawable.avoid);
                        setCard4 = true;
                    }
                    break;

                case "peach":
                    if (!setCard1){
                        card1.setImageResource(R.drawable.peach);
                        setCard1 = true;
                    }else if (!setCard2){
                        card2.setImageResource(R.drawable.peach);
                        setCard2 = true;
                    }else if (!setCard3){
                        card3.setImageResource(R.drawable.peach);
                        setCard3 = true;
                    }else{
                        card4.setImageResource(R.drawable.peach);
                        setCard4 = true;
                    }
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
