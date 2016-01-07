package com.gzfgeh.happytime.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.presenter.IMainPresenter;
import com.gzfgeh.happytime.presenter.IMainView;
import com.gzfgeh.happytime.presenter.MainPresenterImpl;
import com.gzfgeh.happytime.ui.fragment.GifFragment;
import com.gzfgeh.happytime.ui.fragment.SuperAwesomeCardFragment;
import com.gzfgeh.happytime.utils.ThemeUtils;
import com.gzfgeh.happytime.widget.PagerSlidingTabStrip;
import com.gzfgeh.happytime.widget.TabViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView mNavigationView;
    private IMainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        initDrawerLayout();
        initNavigationView();
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void initNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);
        mMainPresenter = new MainPresenterImpl(this);
        switch2News();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mMainPresenter.switchNavigation(menuItem.getItemId());
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
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
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
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
    public void switch2News() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new GifFragment()).commit();
        toolbar.setTitle(R.string.navigation_news);
    }

    @Override
    public void switch2Images() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new GifFragment()).commit();
        toolbar.setTitle(R.string.navigation_images);
    }

    @Override
    public void switch2Weather() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new GifFragment()).commit();
        toolbar.setTitle(R.string.navigation_weather);
    }

    @Override
    public void switch2About() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new GifFragment()).commit();
        toolbar.setTitle(R.string.navigation_about);
    }
}
