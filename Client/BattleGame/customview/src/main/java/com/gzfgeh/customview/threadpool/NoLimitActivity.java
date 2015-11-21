package com.gzfgeh.customview.threadpool;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gzfgeh.customview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by guzhenfu on 15/11/19.
 */
public class NoLimitActivity extends Activity {
    /** 每次只执行一个任务的线程池 */
    private static ExecutorService singleTaskExecutor = null;

    /** 每次执行限定个数个任务的线程池 */
    private static ExecutorService limitedTaskExecutor = null;

    /** 所有任务都一次性开始的线程池 */
    public static ExecutorService allTaskExecutor = null;

    /** 创建一个可在指定时间里执行任务的线程池，亦可重复执行 */
    private static ExecutorService scheduledTaskExecutor = null;

    public static boolean isCancled = false;
    public static int order = 0;
    public static int count = 10;
    private List<AsyncTaskTest> mTaskList = new ArrayList<>(count);
    /** 是否点击并取消任务标示符 */
    private boolean isClick = false;

    static {
        singleTaskExecutor = Executors.newSingleThreadExecutor();// 每次只执行一个线程任务的线程池
        limitedTaskExecutor = Executors.newFixedThreadPool(3);// 限制线程池大小为7的线程池
        allTaskExecutor = Executors.newCachedThreadPool(); // 一个没有限制最大线程数的线程池
        scheduledTaskExecutor = Executors.newScheduledThreadPool(3);// 一个可以按指定时间可周期性的执行的线程池
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_limit_thread_pool);

        ListView listView = (ListView) findViewById(R.id.task_list);
        listView.setAdapter(new AsyncTaskAdapter(this, mTaskList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    List<Runnable> unExecRunn = allTaskExecutor.shutdownNow();
                    allTaskExecutor = null;
                }

                // 以第二项为例来测试是否取消执行的任务
                AsyncTaskTest sat = mTaskList.get(1);
                if (position == 1)
                {
                    if (!isClick)
                    {
                        sat.cancel(true);
                        isCancled = true;
                        isClick = !isClick;
                    }
                    else
                    {
                        sat.cancel(false);
                        isCancled = false;
                        // isClick = false;
                        isClick = !isClick;
                        if (null != sat && sat.getStatus() == AsyncTask.Status.RUNNING)
                        {
                            if (sat.isCancelled())
                            {
                                //sat = new AsyncTaskTest(sat.mTaskItem);
                            }
                            else
                            {
                                Toast.makeText(NoLimitActivity.this, "A task is already running, try later", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                        /**
                         * 由于上面测试关闭，在不重新生成allTaskExecutor的同时，会报异常（没有可以使用的线程池，故此处重新生成线程池对象）
                         */
                        if (allTaskExecutor == null)
                        {
                            allTaskExecutor = Executors.newCachedThreadPool();
                        }
                        sat.executeOnExecutor(allTaskExecutor); // The task is already running(这也是个异常哦，小心使用！ )
                    }
                }
                else
                {
                    sat.cancel(false);
                    isCancled = false;
                    // sat.execute(sat.mTaskItem);
                    // sat.executeOnExecutor(allTaskExecutor);
                }

            }
        });
    }
}
