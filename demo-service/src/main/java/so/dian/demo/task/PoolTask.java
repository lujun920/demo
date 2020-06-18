/*
 * Dian.so Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package so.dian.demo.task;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.stereotype.Component;
import so.dian.mofa3.lang.util.DateUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author ${baizhang}
 * @version $Id: PoolTask.java, v 0.1 2018-12-02 4:05 PM Exp $
 */
@Component
public class PoolTask {

    public void run() {

        boolean flag= false;
        while(flag){
            long t1 = DateUtil.timeStampMilli();
            // 任务2
            ListenableFuture<String> stringTask = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return Thread.currentThread().getName() + "==" + Thread.currentThread().getId() + ">>>Hello World";
                }
            });

            Futures.addCallback(stringTask, new FutureCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    System.err.println(Thread.currentThread().getName() + "==" + Thread.currentThread().getId() + ">>>StringTask: " + result);
                }

                @Override
                public void onFailure(Throwable t) {
                }
            });


            // 执行时间
            System.err.println(Thread.currentThread().getName() + "==" + Thread.currentThread().getId() + ">>>time: " + (System.currentTimeMillis() - t1));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 创建线程池
     * 当前属于IO型操作，核心线程可以适当设置大一些
     * 线程池大小：cpu核心数 * 5 + 1
     * 线程池队列最大值：500
     * 队列：LinkedBlockingDeque
     * 策略：AbortPolicy
     */
    private ExecutorService pool = new ThreadPoolExecutor(
            2,
            500,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(1024),
            r -> new Thread(r, "TEST_POOL_"),
            new ThreadPoolExecutor.AbortPolicy());
    private ListeningExecutorService executorService = MoreExecutors.listeningDecorator(pool);
}