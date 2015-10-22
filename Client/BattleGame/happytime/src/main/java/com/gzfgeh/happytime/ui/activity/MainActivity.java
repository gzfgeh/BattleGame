package com.gzfgeh.happytime.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.ui.fragment.GifFragment;
import com.gzfgeh.happytime.ui.fragment.SuperAwesomeCardFragment;
import com.gzfgeh.happytime.utils.ThemeUtils;
import com.gzfgeh.happytime.widget.PagerSlidingTabStrip;
import com.gzfgeh.happytime.widget.TabViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDrawerLayout();
        initSlideTab();
    }

    private void initSlideTab() {
        PagerSlidingTabStrip strip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager(),getData());
        pager.setAdapter(adapter);
        strip.setViewPager(pager);
    }

    private List<Fragment> getData() {
        List<Fragment> lists = new ArrayList<>();
        GifFragment fragment = GifFragment.newInstance("gjfjfgj");
        lists.add(fragment);
        fragment = GifFragment.newInstance("sksjhfk");
        lists.add(fragment);
        return lists;
    }


    private void initDrawerLayout(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                toolbar.setTitle(R.string.app_name);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                toolbar.setTitle(R.string.app_name);
            }
        };
        drawerToggle.syncState();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setScrimColor(getResources().getColor(R.color.drawer_scrim_color));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.ab_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, "提交文本：" + s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText(MainActivity.this, "当前文本：" + s, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ShareActionProvider mShareActionProvider =(ShareActionProvider)
                MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setAction(Intent.ACTION_SEND).putExtra(Intent.EXTRA_TEXT, "TEST")
                .setType("text/plain");
        mShareActionProvider.setShareIntent(share);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                Toast.makeText(this, "action setting", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.initToolBar(toolbar);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
