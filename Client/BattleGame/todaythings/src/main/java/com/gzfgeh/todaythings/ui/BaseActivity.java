package com.gzfgeh.todaythings.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.WindowManager;

import com.gzfgeh.todaythings.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import Utils.ThemeUtils;

/**
 * Created by guzhenf on 6/25/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        initStatusWindow();
        setContentView(getContentView());
        initToolBar();
    }

    private void initTheme(){
        ThemeUtils.Theme theme = getCurrentTheme();
        ThemeUtils.changTheme(this, theme);
    }

    private ThemeUtils.Theme getCurrentTheme(){
        int value = 0;
        return ThemeUtils.Theme.mapValueToTheme(value);
    }

    private void initStatusWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getColorPrimary());
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    protected void initToolBar(Toolbar toolbar) {
        if (toolbar == null)
            return;

        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitleTextColor(getResources().getColor(R.color.action_bar_title_color));
        toolbar.collapseActionView();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //add back icon
            getSupportActionBar().setDisplayShowTitleEnabled(true); //display title
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
         }

    }

    public int getColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * ToolBar back event
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void initToolBar();
    protected abstract int getContentView();
}
