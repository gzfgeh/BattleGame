package com.gzfgeh.customview.threadpool;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.WindowManager;

import com.gzfgeh.customview.R;
import com.gzfgeh.customview.ShareUtils;
import com.gzfgeh.customview.ThemeUtils;


/**
 * Created by guzhenf on 6/25/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        initToolBar();
    }

    private void initTheme(){
        int value = ShareUtils.getValue(ThemeUtils.CHANGE_THEME, 0);
        ThemeUtils.Theme theme = ThemeUtils.Theme.mapValueToTheme(value);
        ThemeUtils.changTheme(this, theme);
    }

    protected void initToolBar(Toolbar toolbar) {
        if (toolbar == null)
            return;

        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
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

    protected void initToolBar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initToolBar(mToolbar);
    }
    protected abstract int setContentView();
}
