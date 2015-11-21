package com.gzfgeh.customview.threadpool;

import android.os.AsyncTask;
import android.os.SystemClock;

/**
 * Created by guzhenfu on 15/11/19.
 */
public class AsyncTaskTest extends AsyncTask<Void, Integer, Void> {
    private MyListItem mTaskItem;
    private String id;

    public AsyncTaskTest(MyListItem item){
        mTaskItem = item;
        if (NoLimitActivity.order < NoLimitActivity.count || NoLimitActivity.order == NoLimitActivity.count) {
            id = "执行:" + String.valueOf(++NoLimitActivity.order);
        } else {
            NoLimitActivity.order = 0;
            id = "执行:" + String.valueOf(++NoLimitActivity.order);
        }
    }

    @Override
    protected void onPreExecute() {
        mTaskItem.setTitle(id);
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (!isCancelled() && NoLimitActivity.isCancled == false) {
            int prog = 0;

            /**
             * 下面的while中，小马写了个分支用来做个假象（任务东西刚开始下载的时候，速度快，快下载完成的时候就突然间慢了下来的效果， 大家可以想象一下，类似
             * ：PP手机助手、91手机助手中或其它手机应用中，几乎都有这个假象，开始快，结束时就下载变慢了，讲白了 就是开发的人不想让你在下载到大于一半的时候，也就是快下载完的时候去点取消，你那样得多浪费
             * ！所以造个假象，让你不想去取消而已）
             */
            while (prog < 101) {
                if ((prog > 0 || prog == 0) && prog < 70){
                    SystemClock.sleep(100);
                }else{
                    SystemClock.sleep(300);
                }

                publishProgress(prog); // 更新进度条
                prog++;
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        mTaskItem.setProgress(values[0]); // 设置进度
    }
}
