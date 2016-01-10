package com.gzfgeh.happytime.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.beans.NewsBean;
import com.gzfgeh.happytime.presenter.activity_news_details.INewsDetailModle;
import com.gzfgeh.happytime.presenter.activity_news_details.INewsDetailPresenter;
import com.gzfgeh.happytime.presenter.activity_news_details.NewsDetailPresenter;
import com.gzfgeh.happytime.utils.ImageLoaderUtils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by guzhenfu on 15/11/1.
 */
public class NewsDetailActivity extends AppCompatActivity implements INewsDetailModle {
    @Bind(R.id.ivImage)
    ImageView ivImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.htNewsContent)
    HtmlTextView htNewsContent;

    private NewsBean mNews;
    private INewsDetailPresenter mNewsDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        initToolbar();

        mNews = (NewsBean)getIntent().getSerializableExtra("bean");
        collapsingToolbar.setTitle(mNews.getTitle());

        ImageLoaderUtils.display(this, ivImage, mNews.getImgsrc());
        mNewsDetailPresenter = new NewsDetailPresenter(this);
        mNewsDetailPresenter.loadNewsDetail(mNews.getDocid());
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycle_view_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

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

    @Override
    public void showNewsDetailContent(String content) {
        htNewsContent.setHtmlFromString(content, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }
}
