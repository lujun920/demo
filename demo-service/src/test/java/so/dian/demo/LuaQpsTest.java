/*
 * Dian.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package so.dian.demo;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import so.dian.mofa3.cache.service.SerialNumService;
import so.dian.mofa3.lang.util.DateUtil;

/**
 *
 * TODO
 * @author ${baizhang}
 * @version $Id: LuaQpsTest.java, v 0.1 2018-07-25 下午3:35 Exp $
 */
@Slf4j
public class LuaQpsTest extends BaseTest{

    @Autowired
    SerialNumService serialNumService;

    @Test
    public void qpsTest(){
        for(int i = 0; i< 10; i++){
            runTask(20, 30 * 1000);
        }

    }


    /**
     * 具体压测执行--多线程测试其性能
     *
     * @param taskCount  执行任务数
     * @param runTime    执行时间，单位毫秒
     */
    public void runTask(int taskCount, final long runTime) {
        /**
         * 总执行请求次数
         */
        AtomicLong requestCount = new AtomicLong(0);
        /**
         * RT总和
         */
        AtomicLong rtTotalCount = new AtomicLong(0);
        /**
         * 失败次数
         */
        AtomicLong failCount = new AtomicLong(0);
        try {
            log.info("loading....");
            //稍等上下文加载
            Thread.sleep(2000);
        } catch (Exception e1) {
            log.info(e1.getMessage());
        }

        try {
            log.info(".........task begin........");
            final Date beginDate = new Date();

            long taskSubmitBeginTime = DateUtil.timeStampMilli();
            for (int i = 0; i < taskCount; i++) {
                arynExecutorService.submit((Runnable) () -> {
                    while (true) {
                        long sTime = DateUtil.timeStampMilli();
                        try {
                            //具体的服务执行方法
                            serialNumService.getSerialNum("301");

                            long eTime = DateUtil.timeStampMilli();
                            requestCount.getAndAdd(1);
                            rtTotalCount.getAndAdd(eTime - sTime);
                            long currentTime = DateUtil.timeStampMilli();
                            if ((currentTime - beginDate.getTime()) > runTime) {
                                break;
                            }
                        } catch (Throwable e) {
                            failCount.getAndAdd(1);
                            long eTime = DateUtil.timeStampMilli();
                            long between = eTime - sTime;
                            if (between > 200) {
                                log.info("long long ago=" + between);
                            }
                            log.warn(e.getMessage());
                        }
                    }

                });
            }
            log.info("========================task submit consume time========================"
                    + (DateUtil.timeStampMilli() - taskSubmitBeginTime) + "========================");

            while (true) {
                //主线程检测，超时退出
                long currentTime = DateUtil.timeStampMilli();;
                if ((currentTime - beginDate.getTime() + 1000) > runTime) {
                    break;
                }
            }
            Date endDate = new Date();
            long betweenDate = endDate.getTime() - beginDate.getTime();
            log.info("JVMExeCount=" + requestCount.get());
            log.info("JVM QPS=" + requestCount.get() / (betweenDate / 1000));

            log.info("JVM RT=" + rtTotalCount.get() / requestCount.get());
            log.info("JVMFailCount=" + failCount.get());
            log.info("requestCount=" + requestCount.get() + " rtTotalCount="
                    + rtTotalCount.get() + " failCount=" + failCount.get());

        } catch (Exception e) {
            log.warn("requestCount=" + requestCount.get() + " rtTotalCount="
                    + rtTotalCount.get() + " failCount=" + failCount.get());
            log.warn(e.getMessage());
        }
        log.info("================================================task over================================================");

    }
}