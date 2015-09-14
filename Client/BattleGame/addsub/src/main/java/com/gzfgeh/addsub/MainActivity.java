package com.gzfgeh.addsub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AddSub.ChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddSub view = (AddSub) findViewById(R.id.add_sub);
        view.setChangeListener(this);
    }

    @Override
    public void onChangeListener(int num) {
        Toast.makeText(this, num+"", Toast.LENGTH_SHORT).show();
    }
}
